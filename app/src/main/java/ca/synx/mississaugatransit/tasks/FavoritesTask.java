package ca.synx.mississaugatransit.tasks;

import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import ca.synx.mississaugatransit.handlers.StorageHandler;
import ca.synx.mississaugatransit.models.Favorite;

public class FavoritesTask extends AsyncTask<String, Void, List<Favorite>> {

    private StorageHandler mStorageHandler = StorageHandler.getInstance();
    private IFavoritesTask mListener;

    public FavoritesTask(IFavoritesTask favoriteListener) {
        this.mListener = favoriteListener;
    }

    @Override
    protected List<Favorite> doInBackground(String... params) {

        List<Favorite> favorites = new ArrayList<Favorite>();

        try {
            favorites = mStorageHandler.getFavorites();
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
