import java.io.*;
import java.util.zip.*;

public class Buffer {
    public static final String zipEntryName = "z";

    // serialization to byte array
    static byte[] toByteArray(Serializable obj) throws IOException {
        ByteArrayOutputStream bufOut = new ByteArrayOutputStream();
        try (ObjectOutputStream oos = new ObjectOutputStream(bufOut)) {
            oos.writeObject(obj);
            oos.flush();
            return bufOut.toByteArray();
        }
    }

    // compressed serialization
    static byte[] toZipByteArray(Serializable obj) throws IOException {
        ByteArrayOutputStream bufOut = new ByteArrayOutputStream();
        try (ZipOutputStream zos = new ZipOutputStream(bufOut)) {
            zos.putNextEntry(new ZipEntry(zipEntryName));
            zos.setLevel(Deflater.DEFAULT_COMPRESSION);
            try (ObjectOutputStream oos = new ObjectOutputStream(zos)) {
                oos.writeObject(obj);
                oos.flush();
                zos.closeEntry();
                return bufOut.toByteArray();
            }
        }
    }

    // deserialization
    static Object fromByteArray(byte[] arr) throws IOException, ClassNotFoundException {
        ByteArrayInputStream bufIn = new ByteArrayInputStream(arr);
        try (ObjectInputStream ois = new ObjectInputStream(bufIn)) {
            return ois.readObject();
        }
    }

    // deserialization from compressed form
    static Object fromZipByteArray(byte[] arr) throws IOException, ClassNotFoundException {
        ByteArrayInputStream bufIn = new ByteArrayInputStream(arr);
        try (ZipInputStream zis = new ZipInputStream(bufIn))
        {
            ZipEntry zen = zis.getNextEntry();
            if (zen == null || !zen.getName().equals(zipEntryName))
                throw new IOException("Invalid block format");
            try (ObjectInputStream ois = new ObjectInputStream(zis))
            {
                return ois.readObject();
            }
        }
    }

    // write object to RandomAccessFile
    public static long writeObject(RandomAccessFile file, Serializable obj, boolean zipped) throws IOException {
        long position = file.length();
        file.seek(position);
        byte[] data;
        if (zipped)
        {
            data = toZipByteArray(obj);
            file.writeByte(1);
        }
        else
        {
            data = toByteArray(obj);
            file.writeByte(0);
        }
        file.writeInt(data.length);
        file.write(data);
        file.setLength(file.getFilePointer());
        return position;
    }

    // read object from RandomAccessFile
    public static Object readObject(RandomAccessFile file, long position, boolean[] wasZipped) throws IOException, ClassNotFoundException {
        file.seek(position);
        byte zipped = file.readByte();
        int length = file.readInt();
        byte[] data = new byte[length];
        file.readFully(data);
        if (wasZipped != null)
            wasZipped[0] = (zipped != 0);
        if (zipped == 0)
            return fromByteArray(data);
        else if (zipped == 1)
            return fromZipByteArray(data);
        else
            throw new IOException("Invalid block format");
    }
}
