package dao;


public interface FriendshipDao {
	
	public void createFriendship(int friendIdOne, int friendIdTwo);

	public void removeFriendship(int friendIdOne, int friendIdTwo);

}
