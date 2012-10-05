package act.mashup.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import act.mashup.global.Position;

public class LocalPlacesSearch {

	private static final String DATABASE_URL = "jdbc:mysql://192.168.3.213/travel";
	private static final String USERNAME = "sdp";
	private static final String PASSWORD = "sdp123";
	private static final double RANGE = 0.4;

	private static final String QUERY_STRING = "select * from #TABLE# where latitude between #POSITION_LAT#-#RANGE# and #POSITION_LAT#+#RANGE# and longitude between #POSITION_LON#-#RANGE# and #POSITION_LON#+#RANGE# order by rank desc limit 10";

	public static final String TYPE_HOTEL = "hotel";
	public static final String TYPE_RESORT = "resort";
	public static final String TYPE_RESTAURANT = "restaurant";

	Connection conn = null;
	Statement stmt = null;
	ResultSet rs = null;

	// µ¥ÀýÄ£Ê½
	// private static final LocalPlacesSearch instance = new
	// LocalPlacesSearch();

	public LocalPlacesSearch() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
			stmt = conn.createStatement();
		} catch (Exception e) {
			System.out.println("Initial mysql connection failed.");
		}
	}

	public void RemoveResouce() {

		try {
			if (rs != null)
				rs.close();
		} catch (Exception e) {
		}

		try {
			if (stmt != null)
				stmt.close();
		} catch (Exception e) {
		}

		try {
			if (conn != null)
				conn.close();
		} catch (Exception e) {
		}
	}

	// public static synchronized LocalPlacesSearch getInstance() {
	// return instance;
	// }

	public ResultSet SearchByPosition(Position position, String type) {

		String query = QUERY_STRING.replaceAll("#TABLE#", type).replaceAll("#POSITION_LAT#", Double.toString(position.getLatitude()))
				.replaceAll("#POSITION_LON#", Double.toString(position.getLongitude())).replaceAll("#RANGE#", Double.toString(RANGE));
		try {
			rs = stmt.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}

}
