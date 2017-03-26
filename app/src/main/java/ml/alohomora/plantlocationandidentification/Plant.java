package ml.alohomora.plantlocationandidentification;

/**
 * Created by Ankush on 3/26/2017.
 */

public class Plant {
    int rejectionCount;
    double[] locationLat;
    double[] locationLon;
    String[] imageLeafRef;
    String[] imageFlowerRef;
    String[] imageFruitRef;
    String uploaderId;
    boolean isFullyVerfied;
    String[] commonNames;

    String name;
    int nameVerificationCount;
    String leafSize;
    int leafSizeVerificationCount;
    String leafShape;
    int leafShapeVerificationCount;
    String leafColor;
    int leafColorVerificationCount;
    String fuitShape;
    int fruitShapeVerificationCount;
    String fruitColor;
    int fuitColorVerificationCouunt;
    String leafMargins;
    int leafMarginsVerificationCount;

    public Plant(int rejectionCount, double[] locationLat, double[] locationLon, String[] imageLeafRef, String[] imageFlowerRef, String[] imageFruitRef, String uploaderId, boolean isFullyVerfied, String[] commonNames, String name, int nameVerificationCount, String leafSize, int leafSizeVerificationCount, String leafShape, int leafShapeVerificationCount, String leafColor, int leafColorVerificationCount, String fuitShape, int fruitShapeVerificationCount, String fruitColor, int fuitColorVerificationCouunt, String leafMargins, int leafMarginsVerificationCount) {
        this.rejectionCount = rejectionCount;
        this.locationLat = locationLat;
        this.locationLon = locationLon;
        this.imageLeafRef = imageLeafRef;
        this.imageFlowerRef = imageFlowerRef;
        this.imageFruitRef = imageFruitRef;
        this.uploaderId = uploaderId;
        this.isFullyVerfied = isFullyVerfied;
        this.commonNames = commonNames;
        this.name = name;
        this.nameVerificationCount = nameVerificationCount;
        this.leafSize = leafSize;
        this.leafSizeVerificationCount = leafSizeVerificationCount;
        this.leafShape = leafShape;
        this.leafShapeVerificationCount = leafShapeVerificationCount;
        this.leafColor = leafColor;
        this.leafColorVerificationCount = leafColorVerificationCount;
        this.fuitShape = fuitShape;
        this.fruitShapeVerificationCount = fruitShapeVerificationCount;
        this.fruitColor = fruitColor;
        this.fuitColorVerificationCouunt = fuitColorVerificationCouunt;
        this.leafMargins = leafMargins;
        this.leafMarginsVerificationCount = leafMarginsVerificationCount;
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

    public Plant() {
    }

    public int getRejectionCount() {
        return rejectionCount;
    }

    public void setRejectionCount(int rejectionCount) {
        this.rejectionCount = rejectionCount;
    }

    public double[] getLocationLat() {
        return locationLat;
    }

    public void setLocationLat(double[] locationLat) {
        this.locationLat = locationLat;
    }

    public double[] getLocationLon() {
        return locationLon;
    }

    public void setLocationLon(double[] locationLon) {
        this.locationLon = locationLon;
    }

    public String[] getImageLeafRef() {
        return imageLeafRef;
    }

    public void setImageLeafRef(String[] imageLeafRef) {
        this.imageLeafRef = imageLeafRef;
    }

    public String[] getImageFlowerRef() {
        return imageFlowerRef;
    }

    public void setImageFlowerRef(String[] imageFlowerRef) {
        this.imageFlowerRef = imageFlowerRef;
    }

    public String[] getImageFruitRef() {
        return imageFruitRef;
    }

    public void setImageFruitRef(String[] imageFruitRef) {
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

    public String[] getCommonNames() {
        return commonNames;
    }

    public void setCommonNames(String[] commonNames) {
        this.commonNames = commonNames;
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

    public String getFuitShape() {
        return fuitShape;
    }

    public void setFuitShape(String fuitShape) {
        this.fuitShape = fuitShape;
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

    public int getFuitColorVerificationCouunt() {
        return fuitColorVerificationCouunt;
    }

    public void setFuitColorVerificationCouunt(int fuitColorVerificationCouunt) {
        this.fuitColorVerificationCouunt = fuitColorVerificationCouunt;
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
}
