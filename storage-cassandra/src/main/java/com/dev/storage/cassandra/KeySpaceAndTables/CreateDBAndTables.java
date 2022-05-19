package com.dev.storage.cassandra.KeySpaceAndTables;

import com.dev.storage.cassandra.connection.CassandraDBConnection;
import com.dev.storage.cassandra.dao.CRUDoperations;

public class CreateDBAndTables {

	public static void run() {

		try{

			//"--nodes 18.191.183.46"
			CassandraDBConnection.getInstance().DBconnection("localhost", 9042);

			String dataBase = "CREATE KEYSPACE IF NOT EXISTS college WITH replication = {'class': 'SimpleStrategy', 'replication_factor': 3} ;";

			//String dataBase = "CREATE KEYSPACE IF NOT EXISTS BOODSKAP WITH replication = {'class': 'SimpleStrategy', 'replication_factor': 3} ;";
			
			CassandraDBConnection.getInstance().getSession().execute(dataBase);

			System.out.println("DataBase Created Suucess...");

			String table = "CREATE TABLE IF NOT EXISTS college.students ( id int, name text, department text, nameOfCollege text, yearOfPassing int, PRIMARY KEY((id), name))WITH CLUSTERING ORDER BY (name ASC);";

			//String table = "CREATE TABLE IF NOT EXISTS BOODSKAP.boodskap(rollNo int, name text, PRIMARY KEY((rollNo), name))WITH CLUSTERING ORDER BY (name ASC);";
			
			CassandraDBConnection.getInstance().getSession().execute(table);

			System.out.println("Table Created Sucess...");
			
			//CRUDoperations.getInstanceCRUD().insert1();

			CRUDoperations.getInstanceCRUD().insert();

			CRUDoperations.getInstanceCRUD().get();

			//CRUDoperations.getInstanceCRUD().update();

			//CRUDoperations.getInstanceCRUD().delete();


			String what = "\"department\"= \'ITI\', \"name\" = \'Srini\' ,\"yearofpassing\"=2018";

			String how = "id=105";

			//CRUDoperations.getInstanceCRUD().updateByQuery(what, how);

			String what1 = "\"department\", \"name\"";

			String how1 = "id=105";
			
			CRUDoperations.getInstanceCRUD().searchByQuery(what1, how1);

		}catch (Exception e) {
			e.printStackTrace();
		}
	}



}
