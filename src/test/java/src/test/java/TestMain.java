import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;

public class TestMain {

    @Test
    public void testCompareGuide() throws Exception {

        String[] guide = {"guide-getting-started", "master"};

        CloudHostedGuideConverter.main(guide);

        final File testingGuide = new File("testing-file.md");
        final File newGuide = new File("guide-getting-started.md");

        List<String> a = FileUtils.readLines(testingGuide);
        List<String> b = FileUtils.readLines(newGuide);


        assertEquals("The files sizes differ!", a.size(), b.size());

        for (int i = 0; i < a.size(); ++i) {
            assertEquals("The files differ!", a.get(i).trim(), b.get(i).trim());
        }
    }

    @Test
    public void testMains() throws IOException {

        Scanner s = null;
        FileInputStream ip = null;
        FileInputStream ips = null;

        try {
            //read adoc file from the open liberty guide
            File guide = new File("guide-getting-started/README.adoc");
//            URL url = new URL("https://raw.githubusercontent.com/openliberty/" + guideName + "/" + branch + "/README.adoc");
            s = new Scanner(guide);
//          ArrayList for whole text file
            ArrayList<String> listOfLines = new ArrayList<>();

            Properties prop = new Properties();
            Properties props = new Properties();

            ip = new FileInputStream("loopReplacements.properties");
            ips = new FileInputStream("replacements.properties");

            prop.load(ip);
            props.load(ips);


//          write each line into the file
            while (s.hasNextLine()) {
                String inputLine = s.nextLine() + "\n";

                if (inputLine.startsWith("[.tab_content.windows_section.mac_section]")) {

                    while (!s.nextLine().startsWith("[.tab_content.linux_section]")) {
                        continue;
                    }
                }


                listOfLines.add(inputLine);
            }

            Functions.mains(listOfLines, prop, props);

            for (int i = 0; i < listOfLines.size(); i++) {
                Assert.assertTrue(!listOfLines.get(i).startsWith("[.tab_content.linux_section]"));
                Assert.assertTrue(!listOfLines.get(i).contains("[hotspot"));

            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testBlockRemoves() throws IOException {

        Scanner s = null;
        FileInputStream ip = null;
        FileInputStream ips = null;

        try {
            //read adoc file from the open liberty guide
            File guide = new File("guide-getting-started/README.adoc");
//            URL url = new URL("https://raw.githubusercontent.com/openliberty/" + guideName + "/" + branch + "/README.adoc");
            s = new Scanner(guide);
//          ArrayList for whole text file
            ArrayList<String> listOfLines = new ArrayList<>();

            Properties prop = new Properties();
            Properties props = new Properties();

            ip = new FileInputStream("loopReplacements.properties");
            ips = new FileInputStream("replacements.properties");

            prop.load(ip);
            props.load(ips);


//          write each line into the file
            while (s.hasNextLine()) {
                String inputLine = s.nextLine() + "\n";

                if (inputLine.startsWith("[.tab_content.windows_section.mac_section]")) {

                    while (!s.nextLine().startsWith("[.tab_content.linux_section]")) {
                        continue;
                    }
                }


                listOfLines.add(inputLine);
            }

            Functions.mains(listOfLines, prop, props);

            for (int i = 0; i < listOfLines.size(); i++) {
                Assert.assertTrue(!listOfLines.get(i).equals("--"));

            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testRemoveIrrelevant() throws IOException {

        final String[] startingPhrases = {"//", ":", "[source", "NOTE:", "include::", "[role=", "[.tab_", "image::", "start/", "finish/", "system/", "inventory/"};

        Scanner s = null;
        FileInputStream ip = null;
        FileInputStream ips = null;

        try {
            //read adoc file from the open liberty guide
            File guide = new File("guide-getting-started/README.adoc");
//            URL url = new URL("https://raw.githubusercontent.com/openliberty/" + guideName + "/" + branch + "/README.adoc");
            s = new Scanner(guide);
//          ArrayList for whole text file
            ArrayList<String> listOfLines = new ArrayList<>();

            Properties prop = new Properties();
            Properties props = new Properties();

            ip = new FileInputStream("loopReplacements.properties");
            ips = new FileInputStream("replacements.properties");

            prop.load(ip);
            props.load(ips);


//          write each line into the file
            while (s.hasNextLine()) {
                String inputLine = s.nextLine() + "\n";

                if (inputLine.startsWith("[.tab_content.windows_section.mac_section]")) {

                    while (!s.nextLine().startsWith("[.tab_content.linux_section]")) {
                        continue;
                    }
                }


                listOfLines.add(inputLine);
            }

            for (int i = 0; i < listOfLines.size(); i++) {
                for (int h = 0; h < startingPhrases.length; h++) {
                    Functions.removingIrrelevant(listOfLines, i, startingPhrases, h);
                    Assert.assertTrue(!listOfLines.get(i).startsWith(startingPhrases[h]));
                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testremoveDiagramReference() throws IOException {

        Scanner s = null;
        FileInputStream ip = null;
        FileInputStream ips = null;

        try {
            //read adoc file from the open liberty guide
            File guide = new File("guide-microprofile-reactive-messaging-acknowledgment/README.adoc");
//            URL url = new URL("https://raw.githubusercontent.com/openliberty/" + guideName + "/" + branch + "/README.adoc");
            s = new Scanner(guide);
//          ArrayList for whole text file
            ArrayList<String> listOfLines = new ArrayList<>();

            Properties prop = new Properties();
            Properties props = new Properties();

            ip = new FileInputStream("loopReplacements.properties");
            ips = new FileInputStream("replacements.properties");

            prop.load(ip);
            props.load(ips);


//          write each line into the file
            while (s.hasNextLine()) {
                String inputLine = s.nextLine() + "\n";

                if (inputLine.startsWith("[.tab_content.windows_section.mac_section]")) {

                    while (!s.nextLine().startsWith("[.tab_content.linux_section]")) {
                        continue;
                    }
                }


                listOfLines.add(inputLine);
            }


            for (int i = 0; i < listOfLines.size(); i++) {
                if (listOfLines.get(i).indexOf("diagram") != -1) {
                    Functions.removeDiagramReference(listOfLines, i);
                }

                Assert.assertTrue(!listOfLines.get(i).contains("Diagram") || !listOfLines.get(i).contains("diagram"));
            }
        } catch (
                IOException e) {
            e.printStackTrace();

        }
    }


    @Test
    public void testClone() throws IOException {
        try {

            File newGuide = new File("testing-clone-method.md");
            File testingGuide = new File("clone.md");

            String CommonURL = "include::{common-includes}/gitclone.adoc[]";

            String GuidesCommon = CommonURL.substring(27, CommonURL.length() - 2);

            ArrayList<String> listOfLines = new ArrayList<>();

            listOfLines.add("");

            ImportFunctions.clone(listOfLines, "guide-getting-started", 0, GuidesCommon);

            StringBuilder builder = new StringBuilder();
            for (String value : listOfLines) {
                builder.append(value);
            }

            String text = builder.toString();

            writeToFile(text, "testing-clone-method");

            assertEquals("The files differ!",
                    FileUtils.readFileToString(newGuide, "utf-8"),
                    FileUtils.readFileToString(testingGuide, "utf-8"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeToFile(String str, String guideName)
            throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(guideName + ".md"));
        writer.append("\n" + str);
        writer.close();
    }

    @Test
    public void testLink() throws IOException {
            ArrayList<String> listOfLines = new ArrayList<>();

            listOfLines.add(0, "https://github.com/openliberty/guide-cdi-intro.git[Git repository^]");
            listOfLines.add(1, "http://localhost:9080/inventory/systems[http://localhost:9080/inventory/systems^]");

            Functions.link(listOfLines, 0);
            Functions.link(listOfLines, 1);

            Assert.assertTrue(listOfLines.get(0).equals("[Git repository](https://github.com/openliberty/guide-cdi-intro.git)"));
            Assert.assertTrue(listOfLines.get(1).contains("curl http://localhost:9080/inventory/systems"));
    }

    @Test
    public void testRemoveCommand() throws IOException {

        Scanner s = null;
        FileInputStream ip = null;
        FileInputStream ips = null;

        try {
            //read adoc file from the open liberty guide
            File guide = new File("guide-microprofile-reactive-messaging-acknowledgment/README.adoc");
//            URL url = new URL("https://raw.githubusercontent.com/openliberty/" + guideName + "/" + branch + "/README.adoc");
            s = new Scanner(guide);
//          ArrayList for whole text file
            ArrayList<String> listOfLines = new ArrayList<>();

            Properties prop = new Properties();
            Properties props = new Properties();

            ip = new FileInputStream("loopReplacements.properties");
            ips = new FileInputStream("replacements.properties");

            prop.load(ip);
            props.load(ips);


//          write each line into the file
            while (s.hasNextLine()) {
                String inputLine = s.nextLine() + "\n";

                if (inputLine.startsWith("[.tab_content.windows_section.mac_section]")) {

                    while (!s.nextLine().startsWith("[.tab_content.linux_section]")) {
                        continue;
                    }
                }


                listOfLines.add(inputLine);
            }


            for (int i = 0; i < listOfLines.size(); i++) {
                if (listOfLines.get(i).startsWith("[.tab_content.")) {
                    Functions.removeWindowsCommand(listOfLines, i);
                }
                Assert.assertTrue(!listOfLines.get(i).contains("[.tab_content"));
            }
        } catch (
                IOException e) {
            e.printStackTrace();
        }
    }

}


