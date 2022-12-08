package com.halifaxcarpool.commons.business.directions;

import com.halifaxcarpool.commons.business.PolylineDecoder;
import com.halifaxcarpool.commons.business.beans.LatLng;
import com.halifaxcarpool.customer.business.beans.RideNode;

import java.util.*;

/**
 * Feeds data to all the mock implementation used for test cases.
 */
public class DirectionTestSuiteData {

    private static int COUNTER = 0;

    public static Map<Tuple2, String> locationToPolyLineMap = new HashMap<>();

    public static Map<LatLng, String> latLngToPolylineMap = new HashMap<>();
    public static Map<String, List<LatLng>> polylineToPointsMap = new HashMap<>();

    public static Map<String, List<RideNode>> polyLineToRideNodesMap = new HashMap<>();

    public static final Map<String, LatLng> locationToLatLngMap = new HashMap<>();
    
    static {
        populateRideData1();
        populateRideData2();
        populateRideData3();
        populateRideData4();
        populateRideData5();
        populateRideData6();
        populateRideData7();
        populateMultipleRideData1();
    }

    private static void populateRideData1() {
        String startLocation = "6056 University Ave, Halifax, NS B3H 1W5";
        String endLocation = "6328-6276 Quinpool Rd, Halifax, NS B3L 1A5";
        int rideId = 1;
        LatLng latLngStartLocation = new LatLng(44.637673535803295, -63.58742788841804);
        LatLng latLngEndLocation = new LatLng(44.64486, -63.59926);

        locationToLatLngMap.put(startLocation, latLngStartLocation);
        locationToLatLngMap.put(endLocation, latLngEndLocation);

        String encodedPolyline =
                "}gmoGplbcK]{Be@PwB~@kCdAuBv@p@|Dx@pFtA`JdAbHzAxJhAvHyDxA_FfBcFxBmCvAmBz@eCjAeBp@wD|AaAd@w@uD";

        List<LatLng> latLngs = new ArrayList<>();
        latLngs.add(latLngStartLocation);
        latLngs.add(latLngEndLocation);
        polylineToPointsMap.put(encodedPolyline, latLngs);

        latLngToPolylineMap.put(latLngStartLocation, encodedPolyline);
        latLngToPolylineMap.put(latLngEndLocation, encodedPolyline);
        List<RideNode> rideNodes = prepareRideNodesFrom(encodedPolyline, rideId);
        polyLineToRideNodesMap.put(encodedPolyline, rideNodes);
        Tuple2 tuple2 = new Tuple2(startLocation, endLocation);
        locationToPolyLineMap.put(tuple2, encodedPolyline);
    }

    private static void populateRideData2() {
        String startLocation = "Atlantica Hotel Halifax, 1980 Robie St, Halifax, NS B3H 3G5";
        String endLocation = "Halifax Public Gardens, Spring Garden Rd. &, Summer St, Halifax, NS B3J 3S9";
        int rideId = 2;
        LatLng latLngStartLocation = new LatLng(44.64680117511276, -63.5907166156215);
        LatLng latLngEndLocation = new LatLng(44.64293655131684, -63.581769750341834);

        locationToLatLngMap.put(startLocation, latLngStartLocation);
        locationToLatLngMap.put(endLocation, latLngEndLocation);

        String encodedPolyline = "}~noGn`ccKYcB`@QdBu@tGoCjAg@jB{@GWa@eCcAiG[oB]oB@Q@Y@KHWhCkA^EpCkArAo@EOA@";
        latLngToPolylineMap.put(latLngStartLocation, encodedPolyline);
        latLngToPolylineMap.put(latLngEndLocation, encodedPolyline);

        List<RideNode> rideNodes = prepareRideNodesFrom(encodedPolyline, rideId);
        polyLineToRideNodesMap.put(encodedPolyline, rideNodes);
        Tuple2 tuple2 = new Tuple2(startLocation, endLocation);
        locationToPolyLineMap.put(tuple2, encodedPolyline);

        String customerStartLocation = "Halifax Infirmary @ QEII - Robie Street Entrance, Robie Street, Halifax, NS";
        LatLng customerStartLocationLatLng = new LatLng(44.64680117511276, -63.5907166156215);
        latLngToPolylineMap.put(customerStartLocationLatLng, encodedPolyline);
        locationToLatLngMap.put(customerStartLocation, customerStartLocationLatLng);
    }

    private static void populateRideData3() {
        String encodedPolyline = "}~noGn`ccKYcB`@QdBu@tGoCjAg@jB{@GWa@eCcAiG[oB]oB@Q@Y@KHWhCkA^EpCkArAo@EOA@";
        String customerEndLocation = "Camp Hill Veterans Memorial Building @ QEII Health Sciences Centre";
        LatLng customerStartLocationLatLng = new LatLng(44.644482448668285, -63.58753955388395);
        locationToLatLngMap.put(customerEndLocation, customerStartLocationLatLng);
        latLngToPolylineMap.put(customerStartLocationLatLng, encodedPolyline);
    }

    private static void populateRideData4() {
        String location = "Ardmore Tea Room, Quinpool Road, Halifax, NS";
        LatLng latLng = new LatLng(44.64396411441109, -63.6029380442406);
        locationToLatLngMap.put(location, latLng);
    }

    private static void populateRideData5() {
        String location = "Gorsebrook Park, Lundys Lane, Halifax, NS";
        LatLng latLng = new LatLng(44.63425882711372, -63.582021421228255);
        locationToLatLngMap.put(location, latLng);
    }

    private static void populateRideData6() {
        String startLocation = "Saint Mary's University, Robie Street, Halifax, NS";
        String endLocation = "Dalplex, 6260 South St, Halifax, NS B3H 4R2";
        LatLng latLngStartLocation = new LatLng(44.63153705993981, -63.581374822026966);
        LatLng latLngEndLocation = new LatLng(44.63468262060707, -63.59255426856054);
        int rideId = 12;

        locationToLatLngMap.put(startLocation, latLngStartLocation);
        locationToLatLngMap.put(endLocation, latLngEndLocation);

        String encodedPolyline = "shloG~racKbChPFz@FjDBf@Pt@LXvAjCt@x@{@|BiAbDiAbDgBrFu@tCs@bCoAzD]h@WPYJy@VSiAsAqIEYRIZI";
        latLngToPolylineMap.put(latLngStartLocation, encodedPolyline);
        latLngToPolylineMap.put(latLngEndLocation, encodedPolyline);

        List<RideNode> rideNodes = prepareRideNodesFrom(encodedPolyline, rideId);
        polyLineToRideNodesMap.put(encodedPolyline, rideNodes);
        Tuple2 tuple2 = new Tuple2(startLocation, endLocation);
        locationToPolyLineMap.put(tuple2, encodedPolyline);
    }

    private static void populateRideData7() {
        String startLocation = "6328-6276 Quinpool Rd, Halifax, NS B3L";
        String endLocation = "6056 University Ave, Halifax, NS B3H";
        LatLng latLngStartLocation = new LatLng(44.64486, -63.59926);
        LatLng latLngEndLocation = new LatLng(44.6376735358032, -63.587427888418);

        String encodedPolyline =
                "eunoGhwdcKe@mCm@_DYqBg@iBSgAm@cD{@_F}@cFd@I`@OdAe@pCoAlDwAlEeBrAi@W_BWwAfBo@hDsA|HcDdGiCrFyBOaA";
        int rideId = 11;

        latLngToPolylineMap.put(latLngStartLocation, encodedPolyline);
        latLngToPolylineMap.put(latLngEndLocation, encodedPolyline);
        locationToLatLngMap.put(startLocation, latLngStartLocation);
        locationToLatLngMap.put(endLocation, latLngEndLocation);
        List<RideNode> rideNodes = prepareRideNodesFrom(encodedPolyline, rideId);
        String oppositeRidePolyline = "}gmoGplbcK]{Be@PwB~@kCdAuBv@p@|Dx@pFtA`JdAbHzAxJhAvHyDxA_FfBcFxBmCvAmBz@eCjAeBp@wD|AaAd@w@uD";
        rideNodes.addAll(polyLineToRideNodesMap.get(oppositeRidePolyline));
        polyLineToRideNodesMap.put(encodedPolyline, rideNodes);
        Tuple2 tuple2 = new Tuple2(startLocation, endLocation);
        locationToPolyLineMap.put(tuple2, encodedPolyline);
        List<LatLng> latLngs = new ArrayList<>();
        latLngs.add(latLngStartLocation);
        polylineToPointsMap.put(encodedPolyline, latLngs);
    }

    private static void populateMultipleRideData1() {

        String startLocation = "THE TEN SPOT halifax, South Street, Halifax, NS";
        String middleLocation = "The Vuze, Fenwick Tower, Fenwick Street, Halifax, Nova Scotia";
        String endLocation = "Dalplex, South Street, Halifax, Nova Scotia";

        String polyLineForRideRequest = "mxmoG||~bKl@dE`AjFxAhI`@bC|A|Jb@pCfAxHlBzNl@zDvA`Kz@zFX~A`@~Bj@~Dz@vGnAbJpBfOHf@n@S";
        String polyLineForRide1 = "mxmoG||~bKl@dE`AjFxAhITrAfBs@jBo@d@xC\\rBPpA";
        String polyLineForRide2 = "chmoGh_`cK|AvLiDjA_@LJv@ThBlBjNpDzVr@~DTtAv@xFp@jFjAtInAvJd@tCREZI";

        int rideId1 = 34;
        int rideId2 = 36;

        LatLng latLngStartLocation = new LatLng(44.64023, -63.56959);
        LatLng latLngMiddleLocation = new LatLng(44.63762, -63.57509);
        LatLng latLngEndLocation = new LatLng(44.63444, -63.59291);

        Tuple2 tuple2 = new Tuple2(startLocation, endLocation);

        locationToPolyLineMap.put(tuple2, polyLineForRideRequest);

        List<LatLng> latLngs = new ArrayList<>();
        latLngs.add(latLngStartLocation);
        latLngs.add(latLngMiddleLocation);
        latLngs.add(latLngEndLocation);
        polylineToPointsMap.put(polyLineForRideRequest, latLngs);

        latLngToPolylineMap.put(latLngStartLocation, polyLineForRide1);
        latLngToPolylineMap.put(latLngMiddleLocation, polyLineForRide1);
        List<RideNode> rideNodesOn1 = prepareRideNodesFrom(polyLineForRide1, rideId1);
        polyLineToRideNodesMap.put(polyLineForRide1, rideNodesOn1);

        //Cant add another polyline for the same middle node
        latLngToPolylineMap.put(latLngEndLocation, polyLineForRide2);
        List<RideNode> rideNodesOn2 = prepareRideNodesFrom(polyLineForRide2, rideId2);
        polyLineToRideNodesMap.put(polyLineForRide2, rideNodesOn2);

    }

    private static List<RideNode> prepareRideNodesFrom(String encodedPolyLine, int rideId) {
        List<LatLng> points = PolylineDecoder.decodePolyline(encodedPolyLine);
        List<RideNode> rideNodes = new ArrayList<>();
        Iterator<LatLng> iterator = points.iterator();
        while (iterator.hasNext()) {
            LatLng latLng = iterator.next();
            RideNode rideNode = new RideNode(latLng.latitude, latLng.longitude, rideId, ++COUNTER);
            rideNodes.add(rideNode);
        }
        resetCounter();
        return rideNodes;
    }

    private static void resetCounter() {
        COUNTER = 0;
    }
    
}
