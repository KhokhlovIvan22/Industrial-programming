import java.io.*;
import java.util.*;

public class Main {
    static final String filename = "Baggage.dat";
    static final String filenameBak = "Baggage.~dat";
    static final String idxname = "Baggage.idx";
    static final String idxnameBak = "Baggage.~idx";

    static void deleteFile() {
        new File(filename).delete();
        new File(idxname).delete();
    }

    static void appendFile(String[] args, boolean zipped) throws IOException, ClassNotFoundException {
        Scanner in;
        boolean interactive;
        if (args.length >= 2)
        {
            String fname = args[1];
            String encoding = (args.length >= 3) ? args[2] : "UTF-8";
            in = new Scanner(new File(fname), encoding);
            interactive = false;
        }
        else
        {
            in = new Scanner(System.in);
            interactive = true;
            System.out.println("Enter baggage records (enter any symbol to continue, Ctrl+D to finish)");
        }
        try (RandomAccessFile raf = new RandomAccessFile(filename, "rw");
             Index index = Index.load(idxname))
        {
            raf.seek(raf.length());
            while (in.hasNextLine())
            {
                try
                {
                    Baggage bag = Baggage.read(in, interactive);
                    long pos = Buffer.writeObject(raf, bag, zipped);
                    index.put(bag, pos);
                    System.out.println("Record saved in file '" + filename + "': flight " + bag.getFlightNumber());
                } catch (Exception e)
                {
                    System.out.println("Invalid record: " + e.getMessage());
                }
            }
        }
    }

    private static void deleteBackup() {
        new File(filenameBak).delete();
        new File(idxnameBak).delete();
    }

    private static void backup() {
        deleteBackup();
        new File(filename).renameTo(new File(filenameBak));
        new File(idxname).renameTo(new File(idxnameBak));
    }

    static void printFile() throws IOException, ClassNotFoundException {
        try (RandomAccessFile raf = new RandomAccessFile(filename, "rw"))
        {
            long pos;
            int rec = 0;
            boolean[] wasZipped = new boolean[]{false};
            while ((pos = raf.getFilePointer()) < raf.length())
            {
                Baggage bag = (Baggage) Buffer.readObject(raf, pos, wasZipped);
                System.out.println("#" + (++rec) + ":\n" + bag + "\n");
            }
        }
    }

    static boolean printFile(String[] args, boolean reverse) throws ClassNotFoundException, IOException
    {
        if (args.length != 2) {
            System.err.println("Invalid number of arguments");
            return false;
        }

        try (Index idx = Index.load(idxname);
             RandomAccessFile raf = new RandomAccessFile(filename, "rw")) {

            IndexOne2N ix;
            switch (args[1]) {
                case "f": ix = idx.flights(); break;
                case "t": ix = idx.departures(); break;
                case "d": ix = idx.destinations(); break;
                case "w": ix = idx.weights(); break;
                default:
                    System.err.println("Invalid index specified: " + args[1]);
                    return false;
            }
            NavigableMap<String, Vector<Long>> nav = new TreeMap<>();
            for (Map.Entry<String, Vector<Long>> e : ix.entrySet())
                nav.put(e.getKey(), e.getValue());
            NavigableMap<String, Vector<Long>> view = reverse ? nav.descendingMap() : nav;
            boolean[] wasZipped = new boolean[]{false};
            for (Map.Entry<String, Vector<Long>> e : view.entrySet())
                for (Long pos : e.getValue()) {
                    Baggage bag = (Baggage) Buffer.readObject(raf, pos, wasZipped);
                    System.out.println(bag);
                    System.out.println();
                }
        }
        return true;
    }

    static boolean findByKey(String[] args) throws ClassNotFoundException, IOException {
        if (args.length < 3)
        {
            System.out.println("Invalid number of arguments\nUsage: -f {f|t|d|w} key");
            return false;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 2; i < args.length; i++)
        {
            if (i > 2) sb.append(" ");
            sb.append(args[i]);
        }
        String key = sb.toString();
        try (Index idx = Index.load(idxname);
             RandomAccessFile raf = new RandomAccessFile(filename, "rw"))
        {
            IndexOne2N ix;
            switch (args[1])
            {
                case "f":
                    ix = idx.flights();
                    break;
                case "t":
                    ix = idx.departures();
                    break;
                case "d":
                    ix = idx.destinations();
                    break;
                case "w":
                    ix = idx.weights();
                    break;
                default:
                    System.out.println("Invalid index specified: " + args[1]);
                    return false;
            }
            Long[] positions = ix.get(key);
            if (positions.length == 0)
            {
                System.out.println("Key not found for: " + key);
                return true;
            }
            boolean[] wasZipped = new boolean[]{false};
            for (Long pos : positions)
            {
                Baggage bag = (Baggage) Buffer.readObject(raf, pos, wasZipped);
                System.out.println("Found:\n" + bag);
            }
        }
        return true;
    }

    public static void main(String[] args) {
        try
        {
            if (args.length >= 1)
            {
                switch (args[0])
                {
                    case "-?":
                        System.out.println("Syntax:\n" +
                                "\t-a  [file [encoding]] - append data (*)\n" +
                                "\t-az [file [encoding]] - append data (*), compress every record\n" +
                                "\t-d                    - clear all data\n" +
                                "\t-p                    - print data unsorted\n" +
                                "\t-ps  {f|t|d|w}        - print data sorted by key\n" +
                                "\t-psr {f|t|d|w}        - print data reverse sorted by key\n" +
                                "\t-f   {f|t|d|w} key    - find record by key\n" +
                                "\t-fr  {f|t|d|w} key    - print records > key\n" +
                                "\t-fl  {f|t|d|w} key    - print records < key\n" +
                                "\t-?                    - command line syntax\n" +
                                "   (*) if not specified, encoding for file is utf8\n");
                        break;
                    case "-a":
                        appendFile(args, false);
                        break;
                    case "-az":
                        appendFile(args, true);
                        break;
                    case "-p":
                        printFile();
                        break;
                    case "-ps":
                        if (!printFile(args, false))
                            return;
                        break;
                    case "-psr":
                        if (!printFile(args, true))
                            return;
                        break;
                    case "-d":
                        if (args.length != 1)
                        {
                            System.out.println("Invalid number of arguments");
                            return;
                        }
                        backup();
                        deleteFile();
                        break;
                    case "-f":
                    {
                        Comparator<Baggage> comp;
                        switch (args[1])
                        {
                            case "f":
                                comp = Baggage.byFlight;
                                break;
                            case "t":
                                comp = Baggage.byDeparture;
                                break;
                            case "d":
                                comp = Baggage.byDestination;
                                break;
                            case "w":
                                comp = Baggage.byWeight;
                                break;
                            default:
                                System.out.println("Invalid index specified: " + args[1]);
                                return;
                        }
                        if (!findByKey(args))
                            return;
                        break;
                    }
                    case "-fr":
                    {
                        Comparator<Baggage> comp;
                        switch (args[1])
                        {
                            case "f":
                                comp = Baggage.byFlight.reversed();
                                break;
                            case "t":
                                comp = Baggage.byDeparture.reversed();
                                break;
                            case "d":
                                comp = Baggage.byDestination.reversed();
                                break;
                            case "w":
                                comp = Baggage.byWeight.reversed();
                                break;
                            default:
                                System.out.println("Invalid index specified: " + args[1]);
                                return;
                        }
                        if (!findByKey(args))
                            return;
                        break;
                    }
                    case "-fl":
                    {
                        Comparator<Baggage> comp;
                        switch (args[1])
                        {
                            case "f":
                                comp = Baggage.byFlight;
                                break;
                            case "t":
                                comp = Baggage.byDeparture;
                                break;
                            case "d":
                                comp = Baggage.byDestination;
                                break;
                            case "w":
                                comp = Baggage.byWeight;
                                break;
                            default:
                                System.out.println("Invalid index specified: " + args[1]);
                                return;
                        }
                        if (!findByKey(args))
                            return;
                        break;
                    }
                    default:
                        System.out.println("Option is not realised: " + args[0]);
                }
            }
            else
                System.out.println("Nothing to do! Enter -? to see available options");
        } catch (Exception e)
        {
            System.out.println("Runtime error: " + e);
        }
    }
}
