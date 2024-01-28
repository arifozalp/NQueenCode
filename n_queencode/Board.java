package n_queencode;

import java.util.*;

public class Board {
	private final static int X = 0;
	private final static int Y = 1;
	private final static int neighbors[][] = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

	public final int size;
	private final Set<Integer> pieces = new TreeSet<>();

	Board(int size) {
		this.size = size;
	}

	Board(Board board) {
		this.size = board.size;
		this.pieces.addAll(board.pieces);
	}
	
	Board(int size, int[] xs, int[] ys) {
		this.size = size;
		for (int i=0; i<xs.length; i++) {
			addPiece(xs[i], ys[i]);
		}
	}	
	
	public boolean isInside(int x, int y) {
		return x >= 0 && x < size && y >= 0 && y < size;
	}
	//This method checks whether any position on the board is empty or a piece. 
        //The reason why I use the contain method here is that this method searches the given value in the set and returns true if it exists.
	public boolean hasPiece(int x, int y) {
		if (isInside(x, y)) {
			int key = y * size + x;
			return pieces.contains(key); 
		}
		else
			return false;
	}
	//This method puts the piece in a desired position on the board. 
        //It does this with the add method. Add method puts the value I want in the set in a regular way.
	public void addPiece(int x, int y) {
		if (isInside(x, y)) {
			int key = y * size + x;
                        pieces.add(key);
		}
	}
	//This method deletes a piece on the board at a desired location. It does this with the remove method. 
        //The Remove method searches for the value I want in the set and deletes it from the set if it exists.
	public void removePiece(int x, int y) {
		if (isInside(x, y)) {		
			int key = y * size + x;
			pieces.remove(key);
		}
	}
	//The getSuccessors method finds the neighbors of the pieces on the board, creates a separate board for each, and output shows the boards with the neighbors of the pieces one by one.
	public Collection<Board> getSuccessors() {
            //The reason we use Set here is that after we find the neighbors of the pieces on the board, we use these boards to put them in a certain order.
            Set<Board> Successors = new HashSet<>();
            for(int i=0; i<size ;i++){
                for (int j = 0; j < size; j++) {
                   if (pieces.contains(i*size+j)) {
                     for (int[] neighbor : neighbors) {
                        int newY = i + neighbor[X];
                        int newX = j + neighbor[Y];
                        //The new x and y values we get here indicate the neighbor of any piece on the board. 
                        //In the methods below, the case of x and y values being inside the board and having another piece at that location are tested.
                        if (isInside(newX,newY) && !hasPiece(newX,newY)) {
                            pieces.add(newY * size + newX);
                            int[] xs = new int[size];
                            int[] ys = new int[size];
                            int count=0;
                            for (int location:pieces) {
                                int numberofy=location/5;
                                int numberofx= location%5;
                                ys[count]=numberofy;
                                xs[count]=numberofx;
                                count++;
                            }
                            //In this method, we save the piece whose neighbor we find to our Successors Set. Here the equals method works and compares the non-identity with other boards. 
                            //If we don't define this condition in the equals method, our code may fail or save the same board multiple times.
                            Successors.add(new Board(size,xs,ys));
                            pieces.remove(newY * size + newX);
                        }
                    }
                }
            }
       }
            return Successors;
    }
    @Override
    public int hashCode() {
        return pieces.hashCode() + size;
    }
        //The equals method is an important method. Since we override java uses this method in subclasses of the underlying Collection. 
        //We compare Board classes, which are the classes we want in Equals, and we write them inside the equals method because we don't want them to be the same. 
        //If we do not write this method, we will get a runtime error.
	@Override
	public boolean equals(Object o) {
             if (this == o) {
                return true;
                }
                if (o == null || getClass() != o.getClass()) {
                return false;
                }
                Board other = new Board( (Board) o);
                return size == other.size && pieces.equals(other.pieces);
       }

	
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		for (int y=0; y<size; y++) {
			for (int x=0; x<size; x++) {
				if (hasPiece(x, y))
					sb.append("#");
				else
					sb.append(".");
			}
			sb.append("\n");
		}
		
		return sb.toString();
	}
	
}
