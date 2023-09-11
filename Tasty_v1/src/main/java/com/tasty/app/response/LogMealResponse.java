package com.tasty.app.response;

import java.util.List;
import java.util.Map;

public class LogMealResponse {
    private Form foodFamily;
    private Form foodType;
    private List<Result> segmentation_results;

    public Form getFoodFamily() {
        return foodFamily;
    }

    public void setFoodFamily(Form foodFamily) {
        this.foodFamily = foodFamily;
    }

    public Form getFoodType() {
        return foodType;
    }

    public void setFoodType(Form foodType) {
        this.foodType = foodType;
    }

    public List getSegmentation_results() {
        return segmentation_results;
    }

    public void setSegmentation_results(List segmentation_results) {
        this.segmentation_results = segmentation_results;
    }

//    public LogMealResponse() {
//    }
//
//    public LogMealResponse(Form foodFamily, Form foodType, List segmentation_results) {
//        this.foodFamily = foodFamily;
//        this.foodType = foodType;
//        this.segmentation_results = segmentation_results;
//    }

    public class Form {
        private List<Long> ids;
        private List<Double> probs;
        private List<String> tops;

        public List<Long> getIds() {
            return ids;
        }

        public void setIds(List<Long> ids) {
            this.ids = ids;
        }

        public List<Double> getProbs() {
            return probs;
        }

        public void setProbs(List<Double> probs) {
            this.probs = probs;
        }

        public List<String> getTops() {
            return tops;
        }

        public void setTops(List<String> tops) {
            this.tops = tops;
        }

//        public Form() {
//        }
//
//        public Form(List<Long> ids, List<Double> probs, List<String> tops) {
//            this.ids = ids;
//            this.probs = probs;
//            this.tops = tops;
//        }
    }

    public class Result {
        private List<Form> recognition_results;

        public List<Form> getRecognition_results() {
            return recognition_results;
        }

        public void setRecognition_results(List<Form> recognition_results) {
            this.recognition_results = recognition_results;
        }

//        public Result() {
//        }
//
//        public Result(List<Form> recognition_results) {
//            this.recognition_results = recognition_results;
//        }
    }
}
