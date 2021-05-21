import java.util.*;
// Isaac Muia 22701047
// Dylan Fellows 22731134

public class MyProject implements Project {
    public boolean allDevicesConnected(int[][] adjlist) {
        boolean allConnected = true;
		LinkedList<int[]> Devices = new LinkedList<int[]>();
		LinkedList<Integer> connectedDevices = new LinkedList<Integer>();//Devices connected to first devices
		if(adjlist.length == 1) { //trivialy connected
		}
		else
		{
			int [] device = adjlist[0];
			if(device.length == 0 & adjlist.length != 1) // device has no connections
			{
				allConnected = false;
			}
			else
			{ 
				for(int d: device)
				{
					connectedDevices.add(d);
					Devices.add(adjlist[d]);
				}	
				while(!Devices.isEmpty())
				{
					LinkedList<Integer> previousConnectedDevices = connectedDevices;
					for(int d: Devices.element())
					{
						boolean in = false;
						for(int connected: previousConnectedDevices)
						{
							if(d == connected)
							{
								in = true;
							}
							else
							{
							}
						}
						if(!in)
						{
							connectedDevices.add(d);
							Devices.add(adjlist[d]);
						}
					}
					Devices.remove();
				}
				if(connectedDevices.size() != adjlist.length) //If first device isn't connected to all devices not all devices are connected
				{
					allConnected = false;
				}
			}
		}
        return allConnected;
    }

    public int numPaths(int[][] adjlist, int src, int dst) {
        int numPaths = 0;

        if (src == dst) 
		{
			return 1;
		}
  
        boolean visited[] = new boolean[adjlist.length];

        LinkedList<Integer> queue = new LinkedList<Integer>();
  
        visited[src] = true;
        queue.add(src);
  
        while (queue.size() != 0)
		{
            int u = queue.poll();

            for (int col = 0; col < adjlist[u].length; col++)
			{
                int vis = adjlist[u][col];
                if(vis==dst)
				{
					numPaths++;
				}
                  if(!visited[vis])
				  {
                    visited[vis] = true;
                	queue.add(vis);
                  }
               }
        }     
  
        return numPaths;
    }

    
    

    

    public int[] closestInSubnet(int[][] adjlist, short[][] addrs, int src, short[][] queries) {
        int [] hops = new int[queries.length];
		int [] sourceDevice = adjlist[src];
		int subnetID = 0;
		
		for(short[] subnet: queries)
		{
			boolean done = false;
			int IP_ID = 0;
			int hop = 0;
			boolean notInNetwork = false;
			boolean found = false;
			while(!done)
			{	
				short[] IP = addrs[IP_ID];
				short[] IP1 = new short [] {IP[0]};
				short[] IP2 = new short []{IP[0],IP[1]};
				short[] IP3 = new short []{IP[0],IP[1],IP[2]};
				short[] IP4 = new short []{IP[0],IP[1],IP[2],IP[3]};
				if(Arrays.equals(subnet,IP1)| Arrays.equals(subnet,IP2)| Arrays.equals(subnet,IP3)| Arrays.equals(subnet,IP4)| subnet.length == 0)
				{
					int temp = findHops(adjlist, IP_ID, src);
					if(temp < hop | found == false)
					{
						hop = temp;
					}
					found = true;
				}
				if(!found & IP_ID == addrs.length - 1)
				{
					notInNetwork = true;
					done = true;
				}
				else if(found & IP_ID == addrs.length - 1)
				{
					done = true;
				}
				IP_ID++;
			}
			if(notInNetwork)
			{
				hops[subnetID] = Integer.MAX_VALUE;
			}
			else
			{
				hops[subnetID] = hop;
			}
			subnetID++;
		}
        return hops;
    }

// queue of how many hops it takes to get to that device
	private int findHops(int[][] adjlist, int dst, int src) {
		int hop = Integer.MAX_VALUE;
        if (src == dst) 
		{
			return 0;
		}

		int [] hopsToDevice = new int[adjlist.length];
        boolean visited[] = new boolean[adjlist.length];
        LinkedList<Integer> queue = new LinkedList<Integer>();
  
        visited[src] = true;
        queue.add(src);
		hopsToDevice[src] = 0;

        while (queue.size() != 0)
		{
			int device = queue.poll();
            for (int col = 0; col < adjlist[device].length; col++)
			{
                int vis = adjlist[device][col];
            
                if(!visited[vis])
		 		{		
					hopsToDevice[vis] = hopsToDevice[device] + 1; 	
                    visited[vis] = true;
                	queue.add(vis);
					if(vis==dst)
					{
					hop = hopsToDevice[vis];
					queue.clear();
					break;
					}
            	}
            }
        }     
  
		return hop;
	}

	private boolean find_dist(int adjlist[][], int src, int dst, int[] dist, int graph[][]){ 
		int len = adjlist.length;

		Arrays.fill(dist, -1);
		dist[src] = 0;

		LinkedList<Integer> queue = new LinkedList<Integer>();
        queue.add(src);

		while (!queue.isEmpty())
		{
			int u = queue.poll();

			for (int i = 0; i < len; i++)
			{
				for (int j = 0; j < adjlist[i].length; j++)
				{
					int v = adjlist[i][j];

					if (dist[v] < 0 && graph[u][v] > 0)
					{
						queue.add(v);
						dist[v] = dist[u]+1;
					}
				}
			}
		}
		return (dist[dst] >= 0);
	}

	private int speed(int adjlist[][], int n[], int dist[], int u, int dst, int f, int graph[][])
	{
        if (u == dst)
		{ 
			return f;
		}

        for(int k = 0; n[u] < adjlist[u].length; n[u]++)
		{
            int v = adjlist[u][n[u]];

            if(dist[v] == dist[u]+1 && graph[u][v] > 0)
			{
                int path_speed = speed(adjlist, n, dist, v, dst, Math.min(f, graph[u][v]), graph);
                if (path_speed > 0)
				{
                    graph[u][v]-=path_speed;
                    graph[v][u]+=path_speed;
                    
                    return path_speed;
                }
            }
        }
        return 0;
    }

    public int maxDownloadSpeed(int[][] adjlist, int[][] speeds, int src, int dst) {
        if (src == dst)
		{
			return -1;
		}

        int max_dl = 0;
        int len = adjlist.length;
		int[][] graph = new int[len][len];
        int[] dist = new int[len];

        for (int i = 0; i < len; i++)
		{
            Arrays.fill(graph[i],0);
            for (int j = 0; j < adjlist[i].length; j++){
                int reach_device = adjlist[i][j];
                graph[i][reach_device] = speeds[i][j];
            }
        }
            
        while (find_dist(adjlist, src, dst, dist, graph)){
            int n[] = new int[len];

            while (true){
                int path_speed = speed(adjlist, n, dist, src, dst, Integer.MAX_VALUE, graph);
                if (path_speed == 0)
				{
					break;
				}
                max_dl += path_speed;
            }
        }

        return max_dl;

        }
    }
