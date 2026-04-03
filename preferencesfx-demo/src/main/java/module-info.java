module com.dlsc.preferencesfx.demo {

    requires com.dlsc.preferencesfx;
    requires com.google.gson;

    exports com.dlsc.preferencesfx.demo;

    opens com.dlsc.preferencesfx.demo;
    opens com.dlsc.preferencesfx.demo.extended;
}
