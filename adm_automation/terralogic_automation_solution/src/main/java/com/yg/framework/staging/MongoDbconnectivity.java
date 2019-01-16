package com.yg.framework.staging;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;

public class MongoDbconnectivity {

	public void updateStatus(String testCase, String module, String build_details, String status, String timeStamp) {
		MongoClient mongoConn = new MongoClient("localhost", 27017);
		 System.out.println("mongo connection established");
		// Ygstaging databse
		DB db = mongoConn.getDB("YgStaging");
//		System.out.println("database name:" + db.getName());
		// table name(collections is created if it's not there)
		DBCollection ygCollection = db.getCollection("YgTestScenerios");
		// add data in mongodb
		BasicDBObject dbObj = new BasicDBObject("testCase", testCase).append("module", module)
				.append("build_details", build_details).append("status", status).append("timeStamp", timeStamp);
		ygCollection.insert(dbObj);
		System.out.println("updated successfully");
		// reterive db data
		DBCursor cursor = ygCollection.find();
		while (cursor.hasNext()) {
			System.out.println(cursor.next());
		}
	}

}
