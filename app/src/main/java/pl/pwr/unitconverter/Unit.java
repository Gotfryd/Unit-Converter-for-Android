package pl.pwr.unitconverter;

public class Unit {

    private String name;
    private float siFactor;
    private String unitCategory;

    public Unit(){

    }

    public Unit(String name, float siFactor, String unitCategory){
        this.name = name;
        this.siFactor = siFactor;
        this.unitCategory = unitCategory;
    }

    public float convertToSiUnit(float valueToConvert){
        return valueToConvert / siFactor;
    }

    public float convertFromSiUnit(float valueToConvert){
        return valueToConvert * siFactor;
    }

    public String toString() {
        return name;
    }


    //gettery i settery
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getSiFactor() {
        return siFactor;
    }

    public void setSiFactor(float siFactor) {
        this.siFactor = siFactor;
    }

    public String getUnitCategory() {
        return unitCategory;
    }

    public void setUnitCategory(String unitCategory) {
        this.unitCategory = unitCategory;
    }


}


// kilometry = Unit('kilometry', 0.001, 'lenght')
// wynikWMetrach = kilometry.convertToSiUnit(valueInEditText)
// centrymetry = Unit('centrymetry', 100, 'lenght)
// wynikiFinalny = centymetry.convertFromSiUnit(wynikWMetrach)
