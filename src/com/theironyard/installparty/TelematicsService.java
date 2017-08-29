package com.theironyard.installparty;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.annotation.processing.FilerException;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class TelematicsService {
    static void report(VehicleInfo vehicleInfo) {

        int VIN = vehicleInfo.getVIN();
        File file = new File("./" + ((Integer) VIN).toString() + ".json");

        try {
            FileWriter fileWriter = new FileWriter(file);
            String newVehicleInstance = "{\"VIN\": \"" + vehicleInfo.getVIN()+"\", \"odometer\": \"" + vehicleInfo.getOdometer()+"\", \"consumption\": \"" + vehicleInfo.getConsumption()+"\", \"lastOilChange\": \"" + vehicleInfo.getLastOilChange()+"\", \"engineSize\": \"" +vehicleInfo.getEngineSize()+"\"}";
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(vehicleInfo);
            fileWriter.write(json);
            fileWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        ArrayList<VehicleInfo> vehicleList = new ArrayList<>();

        try {
            File thisFile = new File(".");
            for(File f: thisFile.listFiles()) {
                if(f.getName().endsWith(".json")) {
                    String json = null;
                    json = new String(Files.readAllBytes(Paths.get(f.getPath())));
                    ObjectMapper mapper = new ObjectMapper();
                    VehicleInfo vehicleInfo1 = mapper.readValue(json, VehicleInfo.class);
                    vehicleList.add(vehicleInfo1);
                }
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }


            double odometerAvg = 0;
            double consumptionAvg = 0;
            double lastOilChangeAvg = 0;
            double engineSizeAvg = 0;
            String nuHTML = "";

            for(VehicleInfo v: vehicleList) {
                odometerAvg += v.getOdometer();
                consumptionAvg += v.getConsumption();
                lastOilChangeAvg += v.getLastOilChange();
                engineSizeAvg += v.getEngineSize();

                nuHTML += "    <tr>\n" +
                        "        <th>VIN</th><th>Odometer (miles)</th><th>Consumption (gallons)</th><th>Last Oil Change</th><th>Engine Size (liters)</th>\n" +
                        "    </tr>\n" +
                        "    <tr>\n" +
                        "        <td align=\"center\">"+v.getVIN()+"</td><td align=\"center\">"+v.getOdometer()+"</td><td align=\"center\">"+v.getConsumption()+"</td><td align=\"center\">"+v.getLastOilChange()+"</td><td align=\"center\">"+v.getEngineSize()+"</td>\n" +
                        "    </tr>\n" ;
            }

            odometerAvg /= vehicleList.size();
            consumptionAvg /= vehicleList.size();
            lastOilChangeAvg /= vehicleList.size();
            engineSizeAvg /= vehicleList.size();

            String html1 = "<!DOCTYPE html>\n" +
                    "<html lang=\"en\">\n" +
                    "<head>\n" +
                    "    <meta charset=\"UTF-8\">\n" +
                    "    <title>Vehicle Telematics Dashboard</title>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "<h1 align=\"center\">Averages for # vehicles</h1>\n" +
                    "<table align=\"center\">\n" +
                    "    <tr>\n" +
                    "        <th>Odometer (miles) |</th><th>Consumption (gallons) |</th><th>Last Oil Change |</th><th>Engine Size (liters)</th>\n" +
                    "    </tr>\n" +
                    "    <tr>\n" +
                    "        <td align=\"center\">"+odometerAvg+"</td><td align=\"center\">"+consumptionAvg+"</td><td align=\"center\">"+lastOilChangeAvg+"</td><td align=\"center\">"+engineSizeAvg+"</td>\n" +
                    "    </tr>\n" +
                    "</table>\n" +
                    "<br>" +
                    "<h1 align=\"center\">History</h1>\n";

            String html2 = html1 +
                    "<table align=\"center\" border=\"1\">\n" + nuHTML  +
                    "</table>\n" + "</body>\n" + "</html>";


        File htmlFile = new File("dashboard.html");
        try {
            FileOutputStream stream = new FileOutputStream(htmlFile, false);
            byte[] myBytes = html2.getBytes();
            stream.write(myBytes);
            stream.close();
        }
        catch(FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }



    }
}
