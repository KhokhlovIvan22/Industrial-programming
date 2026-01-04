import java.io.*;
import java.util.*;
import java.util.zip.*;

class IndexOne2N implements Serializable {
    private static final long serialVersionUID = 1L;
    private final TreeMap<String, Vector<Long>> map;

    public IndexOne2N() {
        this.map = new TreeMap<>();
    }

    public void put(String key, long value) {
        Vector<Long> arr = map.get(key);
        if (arr == null)
        {
            arr = new Vector<>();
            map.put(key, arr);
        }
        arr.add(value);
    }

    public Long[] get(String key) {
        Vector<Long> arr = map.get(key);
        if (arr == null)
            return new Long[0];
        return arr.toArray(new Long[0]);
    }

    public Set<Map.Entry<String, Vector<Long>>> entrySet() {
        return map.entrySet();
    }
}

public class Index implements Serializable, Closeable {
    private static final long serialVersionUID = 1L;

    private final IndexOne2N flights;
    private final IndexOne2N departures;
    private final IndexOne2N destinations;
    private final IndexOne2N weights;

    public Index() {
        flights = new IndexOne2N();
        departures = new IndexOne2N();
        destinations = new IndexOne2N();
        weights = new IndexOne2N();
    }

    public void put(Baggage baggage, long value) {
        flights.put(baggage.getFlightNumber(), value);
        if (baggage.getDeparture() != null)
            departures.put(baggage.getDeparture().toString(), value);
        destinations.put(baggage.getDestination(), value);
        weights.put(Double.toString(baggage.getBaggageWeight()), value);
    }

    public IndexOne2N flights() { return flights; }
    public IndexOne2N departures() { return departures; }
    public IndexOne2N destinations() { return destinations; }
    public IndexOne2N weights() { return weights; }

    public static Index load(String name) throws IOException, ClassNotFoundException {
        Index obj;
        try (FileInputStream file = new FileInputStream(name);
             ZipInputStream zis = new ZipInputStream(file))
        {
            ZipEntry zen = zis.getNextEntry();
            if (zen == null || !zen.getName().equals(Buffer.zipEntryName))
                throw new IOException("Invalid block format");
            try (ObjectInputStream ois = new ObjectInputStream(zis))
            {
                obj = (Index) ois.readObject();
            }
        }
        catch (FileNotFoundException e)
        {
            obj = new Index();
        }
        if (obj != null)
            obj.save(name);
        return obj;
    }

    private transient String filename = null;

    public void save(String name) {
        filename = name;
    }

    public void saveAs(String name) throws IOException {
        try (FileOutputStream file = new FileOutputStream(name);
             ZipOutputStream zos = new ZipOutputStream(file))
        {
            zos.putNextEntry(new ZipEntry(Buffer.zipEntryName));
            zos.setLevel(Deflater.DEFAULT_COMPRESSION);
            try (ObjectOutputStream oos = new ObjectOutputStream(zos))
            {
                oos.writeObject(this);
                oos.flush();
                zos.closeEntry();
            }
        }
    }

    @Override
    public void close() throws IOException {
        if (filename != null)
            saveAs(filename);
    }
}