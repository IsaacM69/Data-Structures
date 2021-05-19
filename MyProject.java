import java.util.*;

// Isaac Muia 22701047
// Dylan Fellows 22731134


public class MyProject implements Project {
    public boolean allDevicesConnected(int[][] adjlist) {
        boolean connected = true;
		LinkedList<int[]> connectedDevices = new LinkedList<int[]>();

		for(int [] device: adjlist)
		{
			if(device.length == 0 & adjlist.length != 1)
			{
				connected = false;
			}
			if(connectedDevices.isEmpty())
			{
				for(int d: device)
				{
					connectedDevices.add(adjlist[d]);
				}
			}
			else
			{
				boolean in = false;
				for(int d: device)
				{
					for(int[] targetDevice: connectedDevices)
					{
						for(int target: targetDevice)
						{
							if(d == target)
							{
								in = true;
							}
						}
					}
				}
				if(in == false)
				{
					connected = false;
				}
				for(int d: device)
				{
					connectedDevices.add(adjlist[d]);
				}
			}
			connectedDevices.add(device);
		}
        return connected;
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
            int list = adjlist[u][n[u]];

            if(dist[list] == dist[u]+1 && graph[u][list] > 0)
			{
                int path_speed = speed(adjlist, n, dist, list, dst, Math.min(f, graph[u][list]), graph);
                if (path_speed > 0)
				{
                    graph[u][list]-=path_speed;
                    graph[list][u]+=path_speed;
                    
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
