import java.util.*;

// Isaac Muia (22701047)
// Dylan Fellows 22731134


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

    
    private boolean bfsDinic(int adjlist[][], int src, int dst, int[] dist, int resGraph[][]){ 
		int n = adjlist.length;
		Arrays.fill(dist,-1);
		dist[src]=0;
		LinkedList<Integer> queue = new LinkedList<Integer>();
        	queue.add(src);

		while (!queue.isEmpty()){
			int u=queue.poll();
			for (int i=0; i<n; i++){
				for (int j=0; j<adjlist[i].length; j++){
					int v=adjlist[i][j];
					if (dist[v]<0 && resGraph[u][v]>0){
						dist[v] = dist[u]+1;
						queue.add(v);
					}
				}
			}
		}
		return dist[dst] >= 0;
	}

    private int flow(int adjlist[][], int ptr[], int dist[], int u, int dst, int f, int resGraph[][]){
        if (u == dst) return f;
        for(; ptr[u]<adjlist[u].length; ptr[u]++){
            int v = adjlist[u][ptr[u]];
            if(dist[v] == dist[u]+1 && resGraph[u][v]>0){
                int path_flow = flow(adjlist, ptr, dist, v, dst, Math.min(f, resGraph[u][v]), resGraph);
                if (path_flow>0){
                    resGraph[u][v]-=path_flow;
                    resGraph[v][u]+=path_flow;
                    
                    return path_flow;
                }
            }
        }
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
        if (src == dst)
		{
			return -1;
		}

        int max_dl = 0;
        int len = adjlist.length;
		int[][] Graph = new int[len][len];
        int[] dist = new int[len];

        for (int i = 0; i < len; i++)
		{
            Arrays.fill(Graph[i],0);
            for (int j = 0; j < adjlist[i].length; j++){
                int reachDevice = adjlist[i][j];
                Graph[i][reachDevice] = speeds[i][j];
            }
        }
            
        while (bfsDinic(adjlist, src, dst, dist, Graph)){
            int ptr[] = new int[len];
            while (true){
                int path_speed = flow(adjlist, ptr, dist, src, dst, Integer.MAX_VALUE, Graph);
                if (path_speed==0) break;
                max_dl += path_speed;
            }
        }
        return max_dl;
        }
    }
