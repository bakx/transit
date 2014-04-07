package ca.synx.mississaugatransit.tasks;

import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import ca.synx.mississaugatransit.handlers.DatabaseHandler;
import ca.synx.mississaugatransit.handlers.StorageHandler;
import ca.synx.mississaugatransit.models.Favorite;

public class FavoritesTask extends AsyncTask<String, Void, List<Favorite>> {

    private DatabaseHandler mDatabaseHandler;
    private IFavoritesTask mListener;

    public FavoritesTask(DatabaseHandler databaseHandler, IFavoritesTask favoriteListener) {
        this.mDatabaseHandler = databaseHandler;
        this.mListener = favoriteListener;
    }

    @Override
    protected List<Favorite> doInBackground(String... params) {

        List<Favorite> favorites = new ArrayList<Favorite>();

        try {
            favorites = new StorageHandler(mDatabaseHandler).getFavorites();
        } catch (Exception e) {
            Log.e("FavoritesTask:doInBackground", "" + e.getMessage());
            e.printStackTrace();
        }

        return favorites;
    }

    @Override
    protected void onPostExecute(List<Favorite> favorites) {
        super.onPostExecute(favorites);

        mListener.onFavoritesTaskComplete(favorites);
    }

    public interface IFavoritesTask {
        void onFavoritesTaskComplete(List<Favorite> favorites);
    }
}
