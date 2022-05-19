package com.dev.storage.cassandra;

import com.dev.storage.cassandra.KeySpaceAndTables.CreateDBAndTables;

public class App 
{
	public static void main( String[] args )
	{
		
		CreateDBAndTables.run();

	}
}
