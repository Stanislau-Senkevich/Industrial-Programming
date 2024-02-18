package Labs.Lab_10.tours;

import java.io.*;
import java.util.*;

public class Tours {
    public static void main(String[] args) {
        try {
            if ( args.length >= 1 ) {
                if ( args[0].equals("-?") || args[0].equals("-h")) {
                    System.out.println(
                            "Syntax:\n" +
                                    "\t-a  [file [encoding]] - append data (*)\n" +
                                    "\t-az [file [encoding]] - append data (*), compress every record\n" +
                                    "\t-d                    - clear all data\n" +
                                    "\t-dk  {t|n|d} key      - clear data by key\n" +
                                    "\t-p                    - print data unsorted\n" +
                                    "\t-ps  {t|n|d}          - print data sorted by tour/name/days\n" +
                                    "\t-psr {t|n|d}          - print data reverse sorted by tour/name/days\n" +
                                    "\t-f   {t|n|d} key      - find record by key\n"+
                                    "\t-fr  {t|n|d} key      - find records > key\n"+
                                    "\t-fl  {t|n|d} key      - find records < key\n"+
                                    "\t-?, -h                - command line syntax\n"+
                                    "   (*) if not specified, encoding for file is utf8\n"
                    );
                }
                else if ( args[0].equals( "-a" )) {
                    // Append file with new object from System.in
                    // -a [file [encoding]]
                    appendFile( args, false );
                }
                else if ( args[0].equals( "-az" )) {
                    // Append file with compressed new object from System.in
                    // -az [file [encoding]]
                    appendFile( args, true );
                }
                else if ( args[0].equals( "-p" )) {
                    // Prints data file
                    printFile();
                }
                else if ( args[0].equals( "-ps" )) {
                    // Prints data file sorted by key
                    if (!printFile( args, false )) {
                        System.exit(1);
                    }
                }
                else if ( args[0].equals( "-psr" )) {
                    // Prints data file reverse-sorted by key
                    if (!printFile( args, true )) {
                        System.exit(1);
                    }
                }
                else if ( args[0].equals( "-d" )) {
                    // delete files
                    if ( args.length != 1 ) {
                        System.err.println("Invalid number of arguments");
                        System.exit(1);;
                    }
                    deleteFile();
                }
                else if ( args[0].equals( "-dk" )) {
                    // Delete records by key
                    if (!deleteFile( args )) {
                        System.exit(1);
                    }
                }
                else if ( args[0].equals( "-f" )) {
                    // Find record(s) by key
                    if ( !findByKey( args ) ) {
                        System.exit(1);
                    }
                }
                else if ( args[0].equals( "-fr" )) {
                    // Find record(s) by key large then key
                    if ( !findByKey( args, new KeyCompReverse() ) ) {
                        System.exit(1);
                    }
                }
                else if ( args[0].equals( "-fl" )) {
                    // Find record(s) by key less then key
                    if ( !findByKey( args, new KeyComp() ) ) {
                        System.exit(1);
                    }
                }
                else {
                    System.err.println( "Option is not realised: " + args[0] );
                    System.exit(1);
                }
            }
            else {
                System.err.println( "Tours: Nothing to do! Enter -? for options" );
            }
        }
        catch ( Exception e ) {
            System.err.println( "Run/time error: " + e );
            System.exit(1);
        }
        System.err.println( "Tours finished..." );
        System.exit(0);
    }

    static final String filename    = "Tours.dat";
    static final String filenameBak = "Tours.~dat";
    static final String idxname     = "Tours.idx";
    static final String idxnameBak  = "Tours.~idx";

    // input file encoding:
    private static String encoding = "utf8";
    private static PrintStream tourOut = System.out;

    static Tour readBook(Scanner fin ) throws IOException {
        return Tour.nextRead(fin, tourOut)
                ? Tour.read( fin, tourOut) : null;
    }

    private static void deleteBackup() {
        new File( filenameBak ).delete();
        new File( idxnameBak ).delete();
    }

    static void deleteFile() {
        deleteBackup();
        new File( filename ).delete();
        new File( idxname ).delete();
    }

    private static void backup() {
        deleteBackup();
        new File( filename ).renameTo( new File( filenameBak ));
        new File( idxname ).renameTo( new File( idxnameBak ));
    }

    static boolean deleteFile( String[] args )
            throws ClassNotFoundException, IOException, KeyNotUniqueException {
        //-dk  {t|n|d} key      - clear data by key
        if ( args.length != 3 ) {
            System.err.println( "Invalid number of arguments" );
            return false;
        }
        Long[] poss = null;
        try ( Index idx = Index.load( idxname )) {
            IndexBase pidx = indexByArg( args[1], idx );
            if ( pidx == null ) {
                return false;
            }
            if ( !pidx.contains(args[2]) ) {
                System.err.println( "Key not found: " + args[2] );
                return false;
            }
            poss = pidx.get(args[2]);
        }
        backup();
        Arrays.sort( poss );
        try ( Index idx = Index.load( idxname );
              RandomAccessFile fileBak= new RandomAccessFile(filenameBak, "rw");
              RandomAccessFile file = new RandomAccessFile( filename, "rw")) {
            boolean[] wasZipped = new boolean[] {false};
            long pos;
            while (( pos = fileBak.getFilePointer()) < fileBak.length() ) {
                Tour book = (Tour)
                        Buffer.readObject( fileBak, pos, wasZipped );
                if ( Arrays.binarySearch(poss, pos) < 0 ) { // if not found in deleted
                    long ptr = Buffer.writeObject( file, book, wasZipped[0] );
                    idx.put( book, ptr );
                }
            }
        }
        return true;
    }

    static void appendFile( String[] args, Boolean zipped )
            throws FileNotFoundException, IOException, ClassNotFoundException,
            KeyNotUniqueException {
        if ( args.length >= 2 ) {
            FileInputStream stdin = new FileInputStream( args[1] );
            System.setIn( stdin );
            if (args.length == 3) {
                encoding = args[2];
            }
            // hide output:
            tourOut = new PrintStream("null");
        }
        appendFile( zipped );
    }

    static void appendFile( Boolean zipped )
            throws FileNotFoundException, IOException, ClassNotFoundException,
            KeyNotUniqueException {
        Scanner fin = new Scanner( System.in, encoding );
        tourOut.println( "Enter book data: " );
        try ( Index idx = Index.load( idxname );
              RandomAccessFile raf = new RandomAccessFile( filename, "rw" )) {
            for(;;) {
                Tour book = readBook( fin );
                if ( book == null )
                    break;
                long pos = Buffer.writeObject( raf, book, zipped );
                idx.put( book, pos );
            }
        }
    }

    private static void printRecord( RandomAccessFile raf, long pos )
            throws ClassNotFoundException, IOException {
        boolean[] wasZipped = new boolean[] {false};
        Tour book = (Tour) Buffer.readObject(raf, pos, wasZipped);
        if (wasZipped[0]) {
            System.out.print( " compressed" );
        }
        System.out.println( " record at position " + pos + ": \n" + book );
        System.out.println();
    }

    private static void printRecord( RandomAccessFile raf, String key,
                                     IndexBase pidx ) throws ClassNotFoundException, IOException {
        Long[] poss = pidx.get( key );
        for ( long pos : poss ) {
            System.out.print( "*** Key: " +  key + " points to" );
            printRecord( raf, pos );
        }
    }

    static void printFile()
            throws FileNotFoundException, IOException, ClassNotFoundException {
        long pos;
        int rec = 0;
        try ( RandomAccessFile raf = new RandomAccessFile( filename, "rw" )) {
            while (( pos = raf.getFilePointer()) < raf.length() ) {
                System.out.print( "#" + (++rec ));
                printRecord( raf, pos);
            }
            System.out.flush();
        }
    }

    private static IndexBase indexByArg( String arg, Index idx ) {
        IndexBase pidx = null;
        if ( arg.equals("t")) {
            pidx = idx.tourNames;
        }
        else if ( arg.equals("n")) {
            pidx = idx.names;
        }
        else if ( arg.equals("d")) {
            pidx = idx.dayAmounts;
        }
        else {
            System.err.println( "Invalid index specified: " + arg );
        }
        return pidx;
    }

    static boolean printFile( String[] args, boolean reverse )
            throws ClassNotFoundException, IOException {
        if ( args.length != 2 ) {
            System.err.println( "Invalid number of arguments" );
            return false;
        }
        try ( Index idx = Index.load( idxname );
              RandomAccessFile raf = new RandomAccessFile( filename, "rw" )) {
            IndexBase pidx = indexByArg( args[1], idx );
            if ( pidx == null ) {
                return false;
            }
            String[] keys;
            if (args[1].equals("d")) {
                keys = pidx.getKeys( reverse ? new KeyCompIntegerReverse() : new KeyCompInteger() );
            } else {
                keys = pidx.getKeys( reverse ? new KeyCompReverse() : new KeyComp() );
            }

            for ( String key : keys ) {
                printRecord( raf, key, pidx );
            }
        }
        return true;
    }

    static boolean findByKey( String[] args )
            throws ClassNotFoundException, IOException {
        if ( args.length != 3 ) {
            System.err.println( "Invalid number of arguments" );
            return false;
        }
        try ( Index idx = Index.load( idxname );
              RandomAccessFile raf = new RandomAccessFile( filename, "rw" )) {
            IndexBase pidx = indexByArg( args[1], idx );
            if ( !pidx.contains(args[2]) ) {
                System.err.println( "Key not found: " + args[2] );
                return false;
            }
            printRecord( raf, args[2], pidx );
        }
        return true;
    }

    static boolean findByKey( String[] args, Comparator<String> comp)
            throws ClassNotFoundException, IOException {
        if ( args.length != 3 ) {
            System.err.println( "Invalid number of arguments" );
            return false;
        }
        try ( Index idx = Index.load( idxname );
              RandomAccessFile raf = new RandomAccessFile( filename, "rw" )) {
            IndexBase pidx = indexByArg( args[1], idx );
            if ( !pidx.contains(args[2]) ) {
                System.err.println( "Key not found: " + args[2] );
                return false;
            }
            String[] keys = pidx.getKeys(comp);
            for ( int i = 0; i < keys.length; i++ ) {
                String key = keys[i];
                if ( key.equals( args[2] )) {
                    break;
                }
                printRecord( raf, key, pidx );
            }
        }
        return true;
    }
}
