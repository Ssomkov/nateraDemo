package models;

public class Triangle {

    private String id;
    private float firstSide;
    private float secondSide;
    private float thirdSide;

    public Triangle(String id, float firstSide, float secondSide, float thirdSide) {
        this.id = id;
        this.firstSide = firstSide;
        this.secondSide = secondSide;
        this.thirdSide = thirdSide;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public float getFirstSide() {
        return firstSide;
    }

    public void setFirstSide(float firstSide) {
        this.firstSide = firstSide;
    }

    public float getSecondSide() {
        return secondSide;
    }

    public void setSecondSide(float secondSide) {
        this.secondSide = secondSide;
    }

    public float getThirdSide() {
        return thirdSide;
    }

    public void setThirdSide(float thirdSide) {
        this.thirdSide = thirdSide;
    }
}
