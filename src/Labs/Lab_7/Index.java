package Labs.Lab_7;

import java.io.*;
import java.util.*;
import java.util.zip.*;

class KeyComp implements Comparator<String> {
    public int compare(String o1, String o2) {
        // right order:
        return o1.compareTo(o2);
    }
}

class KeyCompInteger implements Comparator<String> {
    public int compare(String o1, String o2) {
        int n1, n2;
        n1 = Integer.parseInt(o1);
        n2 = Integer.parseInt(o2);
        return n1-n2;
    }
}

class KeyCompReverse implements Comparator<String> {
    public int compare(String o1, String o2) {
        // reverse order:
        return o2.compareTo(o1);
    }
}

class KeyCompIntegerReverse implements Comparator<String> {
    public int compare(String o1, String o2) {
        int n1, n2;
        n1 = Integer.parseInt(o1);
        n2 = Integer.parseInt(o2);
        return n2-n1;
    }
}

interface IndexBase {
    String[] getKeys(Comparator<String> comp);

    void put(String key, long value);

    boolean contains(String key);

    Long[] get(String key);
}

class IndexOne2One implements Serializable, IndexBase {
    // Unique keys
    // class release version:
    @Serial
    private static final long serialVersionUID = 1L;

    private final TreeMap<String, Long> map;

    public IndexOne2One() {
        map = new TreeMap<String, Long>();
    }

    public String[] getKeys(Comparator<String> comp) {
        String[] result = map.keySet().toArray(new String[0]);
        Arrays.sort(result, comp);
        return result;
    }

    public void put(String key, long value) {
        map.put(key, value);
    }

    public boolean contains(String key) {
        return map.containsKey(key);
    }

    public Long[] get(String key) {
        long pos = map.get(key);
        return new Long[] { pos };
    }
}

class IndexOne2N implements Serializable, IndexBase {
    // Not unique keys
    // class release version:
    @Serial
    private static final long serialVersionUID = 1L;

    private final TreeMap<String, Vector<Long>> map;

    public IndexOne2N() {
        map = new TreeMap<String, Vector<Long>>();
    }

    public String[] getKeys(Comparator<String> comp) {
        String[] result = map.keySet().toArray(new String[0]);
        Arrays.sort(result, comp);
        return result;
    }

    public void put(String key, long value) {
        Vector<Long> arr = map.get(key);
        if (arr == null) {
            arr = new Vector<Long>();
        }
        arr.add(value);
        map.put(key, arr);
    }

    public void put(String keys, // few keys in one string
                    String keyDel, // key delimiter
                    long value) {
        StringTokenizer st = new StringTokenizer(keys, keyDel);
        int num = st.countTokens();
        for (int i = 0; i < num; i++) {
            String key = st.nextToken();
            key = key.trim();
            put(key, value);
        }
    }

    public boolean contains(String key) {
        return map.containsKey(key);
    }

    public Long[] get(String key) {
        return map.get(key).toArray(new Long[0]);
    }
}

public class Index implements Serializable, Closeable {
    // class release version:
    @Serial
    private static final long serialVersionUID = 1L;

    IndexOne2N names;
    IndexOne2N dayAmounts;
    IndexOne2N tourNames;

    public void put(Tour tour, long value) throws KeyNotUniqueException {
        tourNames.put(tour.tourName, value);
        dayAmounts.put(String.format("%d", tour.dayAmount), value);
        names.put(tour.name, value);
    }

    public Index() {
        names = new IndexOne2N();
        dayAmounts = new IndexOne2N();
        tourNames = new IndexOne2N();
    }

    public static Index load(String name) throws IOException,
            ClassNotFoundException {
        Index obj = null;
        try {
            FileInputStream file = new FileInputStream(name);
            try (ZipInputStream zis = new ZipInputStream(file)) {
                ZipEntry zen = zis.getNextEntry();
                if (!zen.getName().equals(Buffer.zipEntryName)) {
                    throw new IOException("Invalid block format");
                }
                try (ObjectInputStream ois = new ObjectInputStream(zis)) {
                    obj = (Index) ois.readObject();
                }
            }
        } catch (FileNotFoundException e) {
            obj = new Index();
        }
        if (obj != null) {
            obj.save(name);
        }
        return obj;
    }

    private transient String filename = null;

    public void save(String name) {
        filename = name;
    }

    public void saveAs(String name) throws IOException {
        FileOutputStream file = new FileOutputStream(name);
        try (ZipOutputStream zos = new ZipOutputStream(file)) {
            zos.putNextEntry(new ZipEntry(Buffer.zipEntryName));
            zos.setLevel(ZipOutputStream.DEFLATED);
            try (ObjectOutputStream oos = new ObjectOutputStream(zos)) {
                oos.writeObject(this);
                oos.flush();
                zos.closeEntry();
                zos.flush();
            }
        }
    }

    public void close() throws IOException {
        saveAs(filename);
    }
}
