Queue<Integer> distances = new LinkedList<Integer>();
					Queue<Integer> q = new LinkedList<Integer>();
					int [] colour = new int[g.getNumberOfVertices()];
					for(int index = 0; index < colour.length; index++)
					{
						colour[index] = 0;
					}
					q.offer(startVertex);
					while(!q.isEmpty())
					{
						int current = q.remove();
						for(int i = 0; i < g.getNumberOfVertices(); i++)
						{
							if(g.getWeight(current,i) >=1 & colour[i] == 0)
							{
								distance[i] = distance[current] + 1;
								colour[i] = 1;
								q.offer(i);
							}
						}
						colour[current] = 2;
					}
					distance[0] = 0;
					