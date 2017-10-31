package com.example.seanreddy.movieinfo.models;


import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.List;

    public class MovieDataBase {

        private int page;
        @SerializedName("total_results")
        private int totalResults;
        @SerializedName("total_pages")
        private int totalPages;
        private List<Results> results;

        public static MovieDataBase objectFromData(String str) {

            return new Gson().fromJson(str, MovieDataBase.class);
        }

        public int getPage() {
            return page;
        }

        public void setPage(int page) {
            this.page = page;
        }

        public int getTotalResults() {
            return totalResults;
        }

        public void setTotalResults(int totalResults) {
            this.totalResults = totalResults;
        }

        public int getTotalPages() {
            return totalPages;
        }

        public void setTotalPages(int totalPages) {
            this.totalPages = totalPages;
        }

        public List<Results> getResults() {
            return results;
        }

        public void setResults(List<Results> results) {
            this.results = results;
        }
}
