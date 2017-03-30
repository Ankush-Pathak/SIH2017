package ml.alohomora.plantlocationandidentification;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ankush on 3/26/2017.
 */

public class Plant {
    int rejectionCount;
    List<Double> locationLat;
    List<Double> locationLon;
    List<String> imageLeafRef;
    List<String> imageFlowerRef;
    List<String> imageFruitRef;
    String uploaderId;
    boolean isFullyVerfied;
    List<String> commonNames;
    int commonNameVerificationCount;



    String name;
    int nameVerificationCount;
    String leafSize;
    int leafSizeVerificationCount;
    String leafShape;
    int leafShapeVerificationCount;
    String leafColor;
    int leafColorVerificationCount;
    String fruitShape;
    int fruitShapeVerificationCount;
    String fruitColor;
    int fruitColorVerificationCount;
    String leafMargins;
    int leafMarginsVerificationCount;
    boolean isFruitBearing;
    int isFruitBearingVerificationCount;
    String leafTip;
    int leafTipVerificationCount;
    String leafBase;
    int leafBaseVerificationCount;
    String comments;
    int commentsVerficationCount;


    public Plant() {
        this.rejectionCount = 0;
        this.locationLat = new ArrayList<>();
        this.locationLon = new ArrayList<>();
        this.imageLeafRef = new ArrayList<>();
        this.imageFlowerRef = new ArrayList<>();
        this.imageFruitRef = new ArrayList<>();
        this.uploaderId = null;
        this.isFullyVerfied = false;
        this.commonNames = new ArrayList<>();
        this.name = null;
        this.nameVerificationCount = 0;
        this.leafSize = null;
        this.leafSizeVerificationCount = 0;
        this.leafShape = null;
        this.leafShapeVerificationCount = 0;
        this.leafColor = null;
        this.leafColorVerificationCount = 0;
        this.fruitColor = null;
        this.fruitShapeVerificationCount = 0;
        this.fruitColor = null;
        this.fruitColorVerificationCount = 0;
        this.leafMargins = null;
        this.leafMarginsVerificationCount = 0;
        isFruitBearing = false;
        isFruitBearingVerificationCount = 0;
        leafTip = null;
        leafTipVerificationCount = 0;
        leafBase = null;
        leafBaseVerificationCount = 0;
        comments = null;
        commentsVerficationCount = 0;
        commonNameVerificationCount = 0;
    }

    public Plant(int rejectionCount, List<Double> locationLat, List<Double> locationLon, List<String> imageLeafRef, List<String> imageFlowerRef, List<String> imageFruitRef, String uploaderId, boolean isFullyVerfied, List<String> commonNames, int commonNameVerificationCount, String name, int nameVerificationCount, String leafSize, int leafSizeVerificationCount, String leafShape, int leafShapeVerificationCount, String leafColor, int leafColorVerificationCount, String fruitShape, int fruitShapeVerificationCount, String fruitColor, int fruitColorVerificationCount, String leafMargins, int leafMarginsVerificationCount, boolean isFruitBearing, int isFruitBearingVerificationCount, String leafTip, int leafTipVerificationCount, String leafBase, int leafBaseVerificationCount, String comments, int commentsVerficationCount) {
        this.rejectionCount = rejectionCount;
        this.locationLat = locationLat;
        this.locationLon = locationLon;
        this.imageLeafRef = imageLeafRef;
        this.imageFlowerRef = imageFlowerRef;
        this.imageFruitRef = imageFruitRef;
        this.uploaderId = uploaderId;
        this.isFullyVerfied = isFullyVerfied;
        this.commonNames = commonNames;
        this.commonNameVerificationCount = commonNameVerificationCount;
        this.name = name;
        this.nameVerificationCount = nameVerificationCount;
        this.leafSize = leafSize;
        this.leafSizeVerificationCount = leafSizeVerificationCount;
        this.leafShape = leafShape;
        this.leafShapeVerificationCount = leafShapeVerificationCount;
        this.leafColor = leafColor;
        this.leafColorVerificationCount = leafColorVerificationCount;
        this.fruitShape = fruitShape;
        this.fruitShapeVerificationCount = fruitShapeVerificationCount;
        this.fruitColor = fruitColor;
        this.fruitColorVerificationCount = fruitColorVerificationCount;
        this.leafMargins = leafMargins;
        this.leafMarginsVerificationCount = leafMarginsVerificationCount;
        this.isFruitBearing = isFruitBearing;
        this.isFruitBearingVerificationCount = isFruitBearingVerificationCount;
        this.leafTip = leafTip;
        this.leafTipVerificationCount = leafTipVerificationCount;
        this.leafBase = leafBase;
        this.leafBaseVerificationCount = leafBaseVerificationCount;
        this.comments = comments;
        this.commentsVerficationCount = commentsVerficationCount;
    }

    public int getRejectionCount() {
        return rejectionCount;
    }

    public void setRejectionCount(int rejectionCount) {
        this.rejectionCount = rejectionCount;
    }

    public List<Double> getLocationLat() {
        return locationLat;
    }

    public void setLocationLat(List<Double> locationLat) {
        this.locationLat = locationLat;
    }

    public List<Double> getLocationLon() {
        return locationLon;
    }

    public void setLocationLon(List<Double> locationLon) {
        this.locationLon = locationLon;
    }

    public List<String> getImageLeafRef() {
        return imageLeafRef;
    }

    public void setImageLeafRef(List<String> imageLeafRef) {
        this.imageLeafRef = imageLeafRef;
    }

    public List<String> getImageFlowerRef() {
        return imageFlowerRef;
    }

    public void setImageFlowerRef(List<String> imageFlowerRef) {
        this.imageFlowerRef = imageFlowerRef;
    }

    public List<String> getImageFruitRef() {
        return imageFruitRef;
    }

    public void setImageFruitRef(List<String> imageFruitRef) {
        this.imageFruitRef = imageFruitRef;
    }

    public String getUploaderId() {
        return uploaderId;
    }

    public void setUploaderId(String uploaderId) {
        this.uploaderId = uploaderId;
    }

    public boolean isFullyVerfied() {
        return isFullyVerfied;
    }

    public void setFullyVerfied(boolean fullyVerfied) {
        isFullyVerfied = fullyVerfied;
    }

    public List<String> getCommonNames() {
        return commonNames;
    }

    public void setCommonNames(List<String> commonNames) {
        this.commonNames = commonNames;
    }

    public int getCommonNameVerificationCount() {
        return commonNameVerificationCount;
    }

    public void setCommonNameVerificationCount(int commonNameVerificationCount) {
        this.commonNameVerificationCount = commonNameVerificationCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNameVerificationCount() {
        return nameVerificationCount;
    }

    public void setNameVerificationCount(int nameVerificationCount) {
        this.nameVerificationCount = nameVerificationCount;
    }

    public String getLeafSize() {
        return leafSize;
    }

    public void setLeafSize(String leafSize) {
        this.leafSize = leafSize;
    }

    public int getLeafSizeVerificationCount() {
        return leafSizeVerificationCount;
    }

    public void setLeafSizeVerificationCount(int leafSizeVerificationCount) {
        this.leafSizeVerificationCount = leafSizeVerificationCount;
    }

    public String getLeafShape() {
        return leafShape;
    }

    public void setLeafShape(String leafShape) {
        this.leafShape = leafShape;
    }

    public int getLeafShapeVerificationCount() {
        return leafShapeVerificationCount;
    }

    public void setLeafShapeVerificationCount(int leafShapeVerificationCount) {
        this.leafShapeVerificationCount = leafShapeVerificationCount;
    }

    public String getLeafColor() {
        return leafColor;
    }

    public void setLeafColor(String leafColor) {
        this.leafColor = leafColor;
    }

    public int getLeafColorVerificationCount() {
        return leafColorVerificationCount;
    }

    public void setLeafColorVerificationCount(int leafColorVerificationCount) {
        this.leafColorVerificationCount = leafColorVerificationCount;
    }

    public String getFruitShape() {
        return fruitShape;
    }

    public void setFruitShape(String fruitShape) {
        this.fruitShape = fruitShape;
    }

    public int getFruitShapeVerificationCount() {
        return fruitShapeVerificationCount;
    }

    public void setFruitShapeVerificationCount(int fruitShapeVerificationCount) {
        this.fruitShapeVerificationCount = fruitShapeVerificationCount;
    }

    public String getFruitColor() {
        return fruitColor;
    }

    public void setFruitColor(String fruitColor) {
        this.fruitColor = fruitColor;
    }

    public int getFruitColorVerificationCount() {
        return fruitColorVerificationCount;
    }

    public void setFruitColorVerificationCount(int fruitColorVerificationCount) {
        this.fruitColorVerificationCount = fruitColorVerificationCount;
    }

    public String getLeafMargins() {
        return leafMargins;
    }

    public void setLeafMargins(String leafMargins) {
        this.leafMargins = leafMargins;
    }

    public int getLeafMarginsVerificationCount() {
        return leafMarginsVerificationCount;
    }

    public void setLeafMarginsVerificationCount(int leafMarginsVerificationCount) {
        this.leafMarginsVerificationCount = leafMarginsVerificationCount;
    }

    public boolean isFruitBearing() {
        return isFruitBearing;
    }

    public void setFruitBearing(boolean fruitBearing) {
        isFruitBearing = fruitBearing;
    }

    public int getIsFruitBearingVerificationCount() {
        return isFruitBearingVerificationCount;
    }

    public void setIsFruitBearingVerificationCount(int isFruitBearingVerificationCount) {
        this.isFruitBearingVerificationCount = isFruitBearingVerificationCount;
    }

    public String getLeafTip() {
        return leafTip;
    }

    public void setLeafTip(String leafTip) {
        this.leafTip = leafTip;
    }

    public int getLeafTipVerificationCount() {
        return leafTipVerificationCount;
    }

    public void setLeafTipVerificationCount(int leafTipVerificationCount) {
        this.leafTipVerificationCount = leafTipVerificationCount;
    }

    public String getLeafBase() {
        return leafBase;
    }

    public void setLeafBase(String leafBase) {
        this.leafBase = leafBase;
    }

    public int getLeafBaseVerificationCount() {
        return leafBaseVerificationCount;
    }

    public void setLeafBaseVerificationCount(int leafBaseVerificationCount) {
        this.leafBaseVerificationCount = leafBaseVerificationCount;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public int getCommentsVerficationCount() {
        return commentsVerficationCount;
    }

    public void setCommentsVerficationCount(int commentsVerficationCount) {
        this.commentsVerficationCount = commentsVerficationCount;
    }
}
