# RouteFinderCore
Demo Presentation
 - This Module implements core funtionalities for searching shortest route in a given graph satrting from a 
Source node to a destination node.

The Idea for the solution is to:
        - Starting from source node, prepare node matrix that records all possible way a node can be reachable from source node.
        - Out of these records, lets find those paths whre Destination node reachavle from the source node.
        - now calculate the minimum weightage path.
 
 - The Application re-uses (Cache) previously prepared path matrix to calculate shortest paths for other node. and do not
 re-calculate in every request.
 
 - Once source node is change, Application auto-detect and it re-calculate path-matrix for new source node.
 
 - The implementation supports both strategy:
          - Shotest route (Diatance bound)
          - fastest route (Time bound)
          
          
         
