import java.util.*;

// Isaac Muia (22701047)
// Dylan Fellows 22731134
// Nice gary


public class MyProject implements Project {
    public boolean allDevicesConnected(int[][] adjlist) {
        boolean connected = true;
		for(int[] devices: adjlist)
		{
			if(devices.length != (adjlist.length - 1))
			{
				connected = false;
			}
		}
        return connected;
    }

    public int numPaths(int[][] adjlist, int src, int dst) {
        // TODO
        return 0;
    }

    public int[] closestInSubnet(int[][] adjlist, short[][] addrs, int src, short[][] queries) {
        int [] hops = new int[queries.length];
		int [] sourceDevice = adjlist[src];
		
		for(short[] subnet: queries)
		{
			int subnetID = 0;
			for(short[] IP: addrs)
			{	
				short[] IP1 = new short [] {IP[0]};
				short[] IP2 = new short []{IP[0],IP[1]};
				short[] IP3 = new short []{IP[0],IP[1],IP[2]};
				short[] IP4 = new short []{IP[0],IP[1],IP[2],IP[3]};
				if(Arrays.equals(subnet,IP1)| Arrays.equals(subnet,IP2)| Arrays.equals(subnet,IP3)| Arrays.equals(subnet,IP4))
				{
					for(int targetDevice: subnet)
					{
						int distance = 0;
						int [] previousDevice = sourceDevice;
						boolean done = false;
						boolean realyDone = false;
						while(realyDone = false)
						{	
							while(done == false)
							{
								for(int device: previousDevice)
								{
									distance++;
									if(device == targetDevice)
									{
										done = true;
									}
									else
									{
										previousDevice = adjlist[device];
									}
								}
							}
							
						}
					}
				}
				else
				{
					hops[subnetID] = Integer.MAX_VALUE;
				}
				subnetID++;
			}
		}
        return hops;
    }

    public int maxDownloadSpeed(int[][] adjlist, int[][] speeds, int src, int dst) {
        // TODO
        return 0;
    }
}