package rezero4j;

public final class NsEnvironment {
    private final String home;
    private final String separator;
    private String workingDir;
    private String dataDir;

    public NsEnvironment() {
        home = System.getProperty("user.home");
        separator = System.getProperty("file.separator");
        // Stub
        /**
         * ~/.NsEnvironmentからWorkingDirとdataDirを読み込む＆書き出す
         */
        workingDir = home + separator + "Documents/IdeaProjects";
        dataDir = workingDir + separator + "data" + separator;
    }

    public static void main(String[] args) {
        NsEnvironment env = new NsEnvironment();
        System.out.println( System.getProperty("user.home"));
        System.out.println( env.getWorkingDir());
        System.out.println( env.getDataDir());
        env.setDataDir("Documents/data");
        System.out.println( env.getDataDir());
    }

    public String getWorkingDir() {
        return workingDir;
    }

    public void setWorkingDir(String workingDir) {
        this.workingDir = home + separator + workingDir + separator;
    }

    public String getDataDir() {
        return dataDir;
    }

    public void setDataDir(String dataDir) {
        this.dataDir = home + separator + dataDir + separator;
    }

    public String getSeparator() {
        return separator;
    }
}
