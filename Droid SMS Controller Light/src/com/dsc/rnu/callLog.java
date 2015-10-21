package com.dsc.rnu;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;
public class callLog {
	 public static final String AUTHORITY = "call_log";

	    /**
	     * The content:// style URL for this provider
	     */
	    public static final Uri CONTENT_URI =
	        Uri.parse("content://" + AUTHORITY);

	    /**
	     * Contains the recent calls.
	     */
	    public static class Calls implements BaseColumns {
	        /**
	         * The content:// style URL for this table
	         */
	        public static final Uri CONTENT_URI =
	                Uri.parse("content://call_log/calls");

	        /**
	         * The content:// style URL for filtering this table on phone numbers
	         */
	        public static final Uri CONTENT_FILTER_URI =
	                Uri.parse("content://call_log/calls/filter");

	        /**
	         * The default sort order for this table
	         */
	        public static final String DEFAULT_SORT_ORDER = "date DESC";

	        /**
	         * The MIME type of {@link #CONTENT_URI} and {@link #CONTENT_FILTER_URI}
	         * providing a directory of calls.
	         */
	        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/calls";

	        /**
	         * The MIME type of a {@link #CONTENT_URI} sub-directory of a single
	         * call.
	         */
	        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/calls";

	        /**
	         * The type of the call (incoming, outgoing or missed).
	         * <P>Type: INTEGER (int)

	         */
	        public static final String TYPE = "type";

	        public static final int INCOMING_TYPE = 1;
	        public static final int OUTGOING_TYPE = 2;
	        public static final int MISSED_TYPE = 3;

	        /**
	         * The phone number as the user entered it.
	         * <P>Type: TEXT

	         */
	        public static final String NUMBER = "number";

	        /**
	         * The date the call occured, in milliseconds since the epoch
	         * <P>Type: INTEGER (long)

	         */
	        public static final String DATE = "date";

	        /**
	         * The duration of the call in seconds
	         * <P>Type: INTEGER (long)

	         */
	        public static final String DURATION = "duration";

	        /**
	         * Whether or not the call has been acknowledged
	         * <P>Type: INTEGER (boolean)

	         */
	        public static final String NEW = "new";

	        /**
	         * The cached name associated with the phone number, if it exists.
	         * This value is not guaranteed to be current, if the contact information
	         * associated with this number has changed.
	         * <P>Type: TEXT

	         */
	        public static final String CACHED_NAME = "name";

	        /**
	         * The cached number type (Home, Work, etc) associated with the
	         * phone number, if it exists.
	         * This value is not guaranteed to be current, if the contact information
	         * associated with this number has changed.
	         * <P>Type: INTEGER

	         */
	        public static final String CACHED_NUMBER_TYPE = "numbertype";

	        /**
	         * The cached number label, for a custom number type, associated with the
	         * phone number, if it exists.
	         * This value is not guaranteed to be current, if the contact information
	         * associated with this number has changed.
	         * <P>Type: TEXT

	         */
	        public static final String CACHED_NUMBER_LABEL = "numberlabel";

	        /**
	         * Query the call log database for the last dialed number.
	         * @param context Used to get the content resolver.
	         * @return The last phone number dialed (outgoing) or an empty
	         * string if none exist yet.
	         */
	        public static String getLastOutgoingCall(Context context) {
	                final ContentResolver resolver = context.getContentResolver();
		            Cursor c = null;
		            try {
		            	c = resolver.query(CONTENT_URI,new String[] {NUMBER,TYPE,DURATION},null,null,DEFAULT_SORT_ORDER + " LIMIT 5");
			                if (c == null || !c.moveToFirst()) {
			                    return context.getString(R.string.smsCallLog);
			                }
			                String log= "";
			                do{
			                String type = c.getString(c.getColumnIndex(TYPE));
			                if(type.equals("1"))
			                	type = context.getString(R.string.CallLogTypeIC);
			                else if(type.equals("2"))
			                	type = context.getString(R.string.CallLogTypeOT);
			                else if(type.equals("3"))
			                	type = context.getString(R.string.CallLogTypeMS);
			                log = log + c.getString(0)+" / "+type+" / "+c.getString(c.getColumnIndex(DURATION))+"\n";
			                }while(c.moveToNext());
			                return log;
		                } finally {
		                if (c != null) c.close();
		            }
		                   
	        }

			static void removeExpiredEntries(Context context) {
	            final ContentResolver resolver = context.getContentResolver();
	            resolver.delete(CONTENT_URI, "_id IN " +"(SELECT _id FROM calls ORDER BY " + DEFAULT_SORT_ORDER+")", null);
	        }
	    }

		
}
