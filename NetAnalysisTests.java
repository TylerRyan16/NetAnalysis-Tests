package cs1501_p4;

import java.util.ArrayList;

public class NetAnalysisTests {

    // MODIFY FILE PATH AS NEEDED
    private static final String DATA_FILE = "YOUR test_data.txt LOCATION";
    // MODIFY FILE PATH AS NEEDED
    private static final String COPPER_ONLY_FILE = "YOUR copper_only_data.txt LOCATION";
    // MODIFY FILE PATH AS NEEDED
    private static final String STRONG_FILE = "YOUR strong_data.txt LOCATION";
    // MODIFY FILE PATH AS NEEDED
    private static final String SMALL_FILE = "YOUR small_data.txt LOCATION";

    private static void testLowestLatencyPath() {
        System.out.println("~~~~~~~~~~Testing Lowest Latency Path...~~~~~~~~~~");

        // PATHS
        NetAnalysis na = new NetAnalysis(DATA_FILE);
        ArrayList<Integer> path = na.lowestLatencyPath(0, 1);
        ArrayList<Integer> path2 = na.lowestLatencyPath(2, 9);
        ArrayList<Integer> path3 = na.lowestLatencyPath(7, 3);
        ArrayList<Integer> path4 = na.lowestLatencyPath(1, 6);
        ArrayList<Integer> path5 = na.lowestLatencyPath(1, 1);

        // EXPECTED LENGTHS
        int expectedLengthPath = 3; 
        int expectedLengthPath2 = 4; 
        int expectedLengthPath3 = 5; 
        int expectedLengthPath4 = 5;
        int expectedLengthPath5 = 0;

        
         // COMPARE
        System.out.println("Path 1 Length: " + path.size() + " | Expected Length: " + expectedLengthPath + " | " + (path.size() == expectedLengthPath ? "SUCCESS" : "FAIL"));
        System.out.println("Path 2 Length: " + path2.size() + " | Expected Length: " + expectedLengthPath2 + " | " + (path2.size() == expectedLengthPath2 ? "SUCCESS" : "FAIL"));
        System.out.println("Path 3 Length: " + path3.size() + " | Expected Length: " + expectedLengthPath3 + " | " + (path3.size() == expectedLengthPath3 ? "SUCCESS" : "FAIL"));
        System.out.println("Path 4 Length: " + path4.size() + " | Expected Length: " + expectedLengthPath4 + " | " + (path4.size() == expectedLengthPath4 ? "SUCCESS" : "FAIL"));
        System.out.println("Path 5 Length: " + path5.size() + " | Expected Length: " + expectedLengthPath5 + " | " + (path5.size() == expectedLengthPath5 ? "SUCCESS" : "FAIL"));
    }

    private static void testBandwidthAlongPath() {
        System.out.println("\n~~~~~~~~~~ Testing Bandwidth Along Path... ~~~~~~~~~~");

        NetAnalysis na = new NetAnalysis(DATA_FILE);

        // TEST PATH 1
        ArrayList<Integer> path = new ArrayList<>();
        path.add(1); path.add(3); path.add(0); path.add(2); path.add(4); path.add(0);
        int expectedBandwidth = 100; // Assuming the expected bandwidth is known
        int actualBandwidth = na.bandwidthAlongPath(path);
        System.out.println("Expected Bandwidth: " + expectedBandwidth + " | Actual Bandwidth: " + actualBandwidth + " | " + (actualBandwidth == expectedBandwidth ? "SUCCESS" : "FAIL"));

        // TEST PATH 2
        ArrayList<Integer> path2 = new ArrayList<>();
        path2.add(7); path2.add(6); path2.add(5); path2.add(9); path2.add(8); path2.add(4); path2.add(0); path2.add(5); path2.add(6); path2.add(7);
        int expectedBandwidth2 = 100;
        int actualBandwidth2 = na.bandwidthAlongPath(path2);
        System.out.println("Expected Bandwidth: " + expectedBandwidth2 + " | Actual Bandwidth: " + actualBandwidth2 + " | " + (actualBandwidth2 == expectedBandwidth2 ? "SUCCESS" : "FAIL"));

        // TEST PATH 3
        ArrayList<Integer> path3 = new ArrayList<>();
        path3.add(1); path3.add(2); path3.add(3); path3.add(0); path3.add(5); path3.add(9);
        int expectedBandwidth3 = 200;
        int actualBandwidth3 = na.bandwidthAlongPath(path3);
        System.out.println("Expected Bandwidth: " + expectedBandwidth3 + " | Actual Bandwidth: " + actualBandwidth3 + " | " + (actualBandwidth3 == expectedBandwidth3 ? "SUCCESS" : "FAIL"));
        
        // TEST EMPTY PATH
        ArrayList<Integer> emptyPath = new ArrayList<>();
        try {
            na.bandwidthAlongPath(emptyPath);
            System.out.println("Empty Path: Should have thrown IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            System.out.println("Empty Path: SUCCESS (Exception thrown as expected)");
        }

        // TEST PATH WITH 1 VERTEX
        ArrayList<Integer> singleVertexPath = new ArrayList<>();
        singleVertexPath.add(1);
        try {
            na.bandwidthAlongPath(singleVertexPath);
            System.out.println("Single Vertex Path: Should have thrown IllegalArgumentException.");
        } catch (IllegalArgumentException e) {
            System.out.println("Single Vertex Path: SUCCESS (Exception thrown as expected)");
        }
    }

    private static void testCopperOnlyConnected() {
        System.out.println("\n~~~~~~~~~~ Testing Copper-Only Connectivity... ~~~~~~~~~~");

        // TESTING MIXED GRAPH
        NetAnalysis na = new NetAnalysis(DATA_FILE);
        boolean isConnectedNormal = na.copperOnlyConnected();
        System.out.println("Testing on normal graph (mixed connections)");
        System.out.println("Expected: false | Actual: " + isConnectedNormal + " | " + (isConnectedNormal == false ? "SUCCESS" : "FAIL"));

        // TESTING COPPER GRAPH
        NetAnalysis naCopperOnly = new NetAnalysis(COPPER_ONLY_FILE);
        boolean isConnectedCopperOnly = naCopperOnly.copperOnlyConnected();
        System.out.println("\nTesting on copper-only graph");
        System.out.println("Expected: true | Actual: " + isConnectedCopperOnly + " | " + (isConnectedCopperOnly == true ? "SUCCESS" : "FAIL"));
  
    }

    private static void testConnectedTwoVertFail() {
        System.out.println("\n~~~~~~~~~~ Testing Network Resilience with Two Vertices Failure... ~~~~~~~~~~");

        // TEST EMPTY GRAPH
        NetAnalysis naEmpty = new NetAnalysis(); // Assuming this initializes an empty graph
        boolean remainsConnectedEmpty = naEmpty.connectedTwoVertFail();
        System.out.println("Testing on empty graph");
        System.out.println("Expected: false | Actual: " + remainsConnectedEmpty + " | " + (!remainsConnectedEmpty ? "SUCCESS" : "FAIL"));    

        // TEST WEAK FILE
        NetAnalysis na = new NetAnalysis(DATA_FILE);
        boolean remainsConnected = na.connectedTwoVertFail();
        System.out.println("Testing on fragile graph");
        System.out.println("Expected: false | Actual: " + remainsConnected + " | " + (remainsConnected == false ? "SUCCESS" : "FAIL"));
  
        // TEST STRONG FILE
        NetAnalysis naStrong = new NetAnalysis(STRONG_FILE);
        boolean remainsConnected2 = naStrong.connectedTwoVertFail();
        System.out.println("Testing on strong graph");
        System.out.println("Expected: true | Actual: " + remainsConnected2 + " | " + (remainsConnected2 == true ? "SUCCESS" : "FAIL"));

        // TEST SMALL FILE
        NetAnalysis naSmall = new NetAnalysis(SMALL_FILE);
        boolean remainsConnected3 = naSmall.connectedTwoVertFail();
        System.out.println("Testing on small graph (2 vertexes, 1 edge)");
        System.out.println("Expected: false | Actual: " + remainsConnected3 + " | " + (remainsConnected3 == false ? "SUCCESS" : "FAIL"));

    }

    private static void testLowestAvgLatST() {
        System.out.println("\n~~~~~~~~~~ Testing Lowest Average Latency Spanning Tree... ~~~~~~~~~~");

        // TEST STANDARD GRAPH
        NetAnalysis na = new NetAnalysis(DATA_FILE);
        ArrayList<STE> mstData = na.lowestAvgLatST();
        int expectedSizeData = 9;
        System.out.println("Data File MST Size: " + mstData.size() + " | Expected Size: " + expectedSizeData + " | " + (mstData.size() == expectedSizeData ? "SUCCESS" : "FAIL"));
    
        // TEST COPPER GRAPH
        NetAnalysis naCopperOnly = new NetAnalysis(COPPER_ONLY_FILE);
        ArrayList<STE> mstCopperOnly = naCopperOnly.lowestAvgLatST();
        int expectedSizeCopperOnly = 9;
        System.out.println("Copper-Only File MST Size: " + mstCopperOnly.size() + " | Expected Size: " + expectedSizeCopperOnly + " | " + (mstCopperOnly.size() == expectedSizeCopperOnly ? "SUCCESS" : "FAIL"));
    
        // TEST STRONG GRAPH
        NetAnalysis naStrong = new NetAnalysis(STRONG_FILE);
        ArrayList<STE> mstStrong = naStrong.lowestAvgLatST();
        int expectedSizeStrong = 4;
        System.out.println("Strong File MST Size: " + mstStrong.size() + " | Expected Size: " + expectedSizeStrong + " | " + (mstStrong.size() == expectedSizeStrong ? "SUCCESS" : "FAIL"));
    
        // TEST SMALL GRAPH
        NetAnalysis naSmall = new NetAnalysis(SMALL_FILE);
        ArrayList<STE> mstSmall = naSmall.lowestAvgLatST();
        int expectedSizeSmall = 1;
        System.out.println("Small File MST Size: " + mstSmall.size() + " | Expected Size: " + expectedSizeSmall + " | " + (mstSmall.size() == expectedSizeSmall ? "SUCCESS" : "FAIL"));
    }

    public static void main(String[] args) {
        testLowestLatencyPath();
        testBandwidthAlongPath();
        testCopperOnlyConnected();
        testConnectedTwoVertFail();
        testLowestAvgLatST();
    }
}