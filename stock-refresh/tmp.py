
def solution(N,A,B):
  # is N is current node then return True
  if N == 1:
    return True
  
  # initialize the variables
  # queue to store the queue to implement bfs
  queue = [B[i] for i in range(len(A)) if A[i]==1]
  
  # traversed list stores the nodes that are traversed
  traversed = [0]*(max(max(B),max(A))+1)
  
  # get the first element in the queue
  currentNode = queue.pop(0)
  
  # implement BFS
  while currentNode != N:
  
    # mark current node as traversed
    traversed[currentNode] = 1
  
    # add neighbouring nodes to queue
    for i in range(len(A)):
      # if node is already traversed wh do not add them
      if A[i] == currentNode and traversed[B[i]]==0:
        queue.append(B[i])
    
    # if all nodes are traversed then node is not present
    if queue==[]:
      return False
    
    # get first node in queue
    currentNode = queue.pop(0)
      
  # we got the required node
  return True


if __name__ == '__main__':
    
    # A = [1,1,1,2,2,3,3,8,8,9,9]
    # B = [2,7,8,3,6,4,5,9,12,10,11]
    # N = 9
    # print(solution(9,A,B))
    # print(solution(15,A,B))

    A = [1,2,1,3]
    B = [2,4,3,4]
    N = 4
    print(solution(N,A,B))
    #print(solution(15,A,B))