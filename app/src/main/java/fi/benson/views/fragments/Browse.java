package fi.benson.views.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

import fi.benson.R;
import fi.benson.adapters.PostAdapter;
import fi.benson.models.Posts;


/**
 * Created by bkamau on 04/04/16.
 */
public class Browse extends Fragment {


    private List<Posts> posts = new ArrayList<>();

    private RecyclerView recycler;
    private LinearLayoutManager layoutManager;
    private StaggeredGridLayoutManager mStaggeredLayoutManager;
    private PostAdapter postAdapter;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_browse, container, false);



        recycler = (RecyclerView) rootView.findViewById(R.id.mainRecycler);
        mStaggeredLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recycler.setItemAnimator(new DefaultItemAnimator());
        recycler.setLayoutManager(mStaggeredLayoutManager);
        recycler.setHasFixedSize(true);
        postAdapter = new PostAdapter(getContext(), posts);
        recycler.setAdapter(postAdapter);

        loadData();

        return rootView;

    }



    public void loadData() {

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Post");
        query.orderByDescending("createdAt");

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null){
                    for (int i = 0; i < objects.size(); i++){
                        ParseObject object = objects.get(i);
                        Posts post = new Posts();
                        post.setImageUrl(object.getParseFile("image").getUrl());
                        posts.add(post);
                    }
                }

                postAdapter.notifyDataSetChanged();
            }
        });


    }

    public void loadMoreData(int limit) {

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Post");
        query.setLimit(limit).orderByDescending("createdAt");

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null){
                    for (int i = 0; i < objects.size(); i++){
                        ParseObject object = objects.get(i);
                        Posts post = new Posts();
                        post.setImageUrl(object.getParseFile("image").getUrl());
                        posts.add(post);
                    }
                }

                postAdapter.notifyDataSetChanged();
            }
        });


    }


}