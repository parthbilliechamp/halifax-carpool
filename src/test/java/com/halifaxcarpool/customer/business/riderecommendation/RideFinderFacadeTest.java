package com.halifaxcarpool.customer.business.riderecommendation;

import com.halifaxcarpool.commons.business.RideNodeDaoMockImpl;
import com.halifaxcarpool.commons.business.beans.LatLng;
import com.halifaxcarpool.commons.business.geocoding.GeoCodingMockImpl;
import com.halifaxcarpool.commons.business.geocoding.IGeoCoding;
import com.halifaxcarpool.customer.business.beans.RideNode;
import com.halifaxcarpool.customer.business.beans.RideRequest;
import com.halifaxcarpool.customer.business.beans.RideRequestNode;
import com.halifaxcarpool.customer.business.recommendation.DistanceFinder;
import com.halifaxcarpool.commons.business.PolylineDecoder;
import com.halifaxcarpool.customer.business.recommendation.RideFinderFacade;
import com.halifaxcarpool.customer.database.dao.IRideNodeDao;
import com.halifaxcarpool.driver.business.beans.Ride;
import com.halifaxcarpool.driver.database.dao.IRidesDao;
import com.halifaxcarpool.driver.database.dao.RidesDaoMockImpl;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.*;

@SpringBootTest
@ActiveProfiles("test")
public class RideFinderFacadeTest {

    RideFinderFacade rideFinderFacade = new RideFinderFacade();
    IRideNodeDao rideNodeDao = new RideNodeDaoMockImpl();
    IGeoCoding geoCoding = new GeoCodingMockImpl();
    
    IRidesDao ridesDao = new RidesDaoMockImpl();

    @Test
    public void findDirectRouteRidesSameStartPointSameEndPointTest() {
        String startLocation = "6056 University Ave, Halifax, NS B3H 1W5";
        String endLocation = "6328-6276 Quinpool Rd, Halifax, NS B3L 1A5";
        RideRequest rideRequest = new RideRequest(1, 1, startLocation, endLocation);
        List<Ride> rides = rideFinderFacade.findDirectRouteRides(rideRequest, rideNodeDao, geoCoding, ridesDao);
        assert 1 == rides.size();
        Ride ride = rides.get(0);
        assert Objects.equals(ride.startLocation, startLocation);
        assert Objects.equals(ride.endLocation, endLocation);
    }

    @Test
    public void findDirectRouteRidesInWayStartPointSameEndPointTest() {
        RideRequest rideRequest = new RideRequest(1, 1, "", "");
        List<Ride> rides = rideFinderFacade.findDirectRouteRides(rideRequest, rideNodeDao, geoCoding, ridesDao);
        assert 1 == rides.size();
    }

    @Test
    public void findDirectRouteRidesSameStartPointInWayEndPointTest() {
        RideRequest rideRequest = new RideRequest(1, 1, "", "");
        List<Ride> rides = rideFinderFacade.findDirectRouteRides(rideRequest, rideNodeDao, geoCoding, ridesDao);
        assert 1 == rides.size();
    }

    @Test
    public void findDirectRouteRidesInWayStartPointInWayEndPointTest() {
        RideRequest rideRequest = new RideRequest(1, 1, "", "");
        List<Ride> rides = rideFinderFacade.findDirectRouteRides(rideRequest, rideNodeDao, geoCoding, ridesDao);
        assert 1 == rides.size();
    }

    @Test
    public void findDirectRouteRidesDifferentStartPointSameEndPointTest() {
        RideRequest rideRequest = new RideRequest(1, 1, "", "");
        List<Ride> rides = rideFinderFacade.findDirectRouteRides(rideRequest, rideNodeDao, geoCoding, ridesDao);
        assert 1 == rides.size();
    }

    @Test
    public void findDirectRouteRidesSameStartPointDifferentEndPointTest() {
        RideRequest rideRequest = new RideRequest(1, 1, "", "");
        List<Ride> rides = rideFinderFacade.findDirectRouteRides(rideRequest, rideNodeDao, geoCoding, ridesDao);
        assert 1 == rides.size();
    }

    @Test
    public void findDirectRouteRidesDifferentStartPointDifferentEndPointTest() {
        RideRequest rideRequest = new RideRequest(1, 1, "", "");
        List<Ride> rides = rideFinderFacade.findDirectRouteRides(rideRequest, rideNodeDao, geoCoding, ridesDao);
        assert 1 == rides.size();
    }

    @Test
    public void findDirectRouteRidesSelectOnlyMatchingRidesTest() {
        RideRequest rideRequest = new RideRequest(1, 1, "", "");
        List<Ride> rides = rideFinderFacade.findDirectRouteRides(rideRequest, rideNodeDao, geoCoding, ridesDao);
        assert 1 == rides.size();
    }

    @Test
    public void findDirectRouteRidesDoNotSelectRidesInOppositeDirectionTest() {
        RideRequest rideRequest = new RideRequest(1, 1, "", "");
        List<Ride> rides = rideFinderFacade.findDirectRouteRides(rideRequest, rideNodeDao, geoCoding, ridesDao);
        assert 1 == rides.size();
    }

    @Test
    public void findDirectRouteRidesSelectSameDirectionAndDoNotSelectRideInOppositeDirectionTest() {
        RideRequest rideRequest = new RideRequest(1, 1, "", "");
        List<Ride> rides = rideFinderFacade.findDirectRouteRides(rideRequest, rideNodeDao, geoCoding, ridesDao);
        assert 1 == rides.size();
    }

    @Test
    public void test() {
        /**
         * 44.644200  -63.600860
         * 44.645200  -63.610876
         * 44.654200  -63.620854
         * 44.674200  -63.590864
         * 44.634200  -63.580861
         */
        RideNode rideNode1 = new RideNode(44.644200, -63.600860, 1, 1);
        RideNode rideNode2 = new RideNode(44.645200, -63.610876, 2, 2);
        RideNode rideNode3 = new RideNode(44.658200, -63.620854, 2, 2);
        RideNode rideNode4 = new RideNode(44.674200, -63.590864, 1, 1);
        RideNode rideNode5 = new RideNode(44.634200, -63.580861, 3, 3);
        List<RideNode> rideNodeList = new ArrayList<>();
        rideNodeList.add(rideNode1);
        rideNodeList.add(rideNode2);
        rideNodeList.add(rideNode3);
        rideNodeList.add(rideNode4);
        rideNodeList.add(rideNode5);

        RideRequestNode startNode = new RideRequestNode(44.649540, -63.600001, 1);
        RideRequestNode endNode = new RideRequestNode(44.679540, -63.590001, 1);
        Set<Integer> validRidesForStartNode = new HashSet<>();
        Set<Integer> validRidesForEndNode = new HashSet<>();

        int threshold = 1;

        for (RideNode rideNode: rideNodeList) {
            double distanceFromStartNode = DistanceFinder.findDistance(startNode.latitude, rideNode.latitude,
                    startNode.longitude, rideNode.longitude);
            System.out.println(distanceFromStartNode);
            if (distanceFromStartNode <= threshold) {
                validRidesForStartNode.add(rideNode.rideId);
            }
            double distanceFromEndNode = DistanceFinder.findDistance(endNode.latitude, rideNode.latitude,
                    endNode.longitude, rideNode.longitude);
            System.out.println(distanceFromEndNode);
            if (distanceFromEndNode <= threshold) {
                validRidesForEndNode.add(rideNode.rideId);
            }
        }
        validRidesForStartNode.retainAll(validRidesForEndNode);
        List<Integer> resultRides = new ArrayList<>(validRidesForStartNode);
        System.out.println(resultRides);
        //Collections.sort(rideNodeList);
    }

    @Test
    public void test2() {

        String input = "}gmoGplbcK]{Be@PwB~@kCdAuBv@p@|Dx@pFtA`JdAbHzAxJhAvHyDxA_FfBcFxBmCvAmBz@eCjAeBp@wD|AaAd@w@uDE]";
        List<LatLng> points = PolylineDecoder.decodePolyline(input);
        List<RideNode> rideNodeList = new ArrayList<>();
        int counter = 0;
        for (LatLng point: points) {
            rideNodeList.add(new RideNode(point.latitude, point.longitude, 1, counter++));
        }
        for (LatLng point: points) {
            rideNodeList.add(new RideNode(point.latitude, point.longitude, 5, counter--));
        }
        counter = 0;
        String startLocation = "2585 Robie St, Halifax, NS B3K 4N5"; // 44.65462 -63.59480
        String endLocation = "6328-6276 Quinpool Rd, Halifax, NS B3L 1A5"; // 44.64486 -63.59926
        input = "krpoGnzccKgBjCcExGeCnEoBdD{AvCmAtBmCtEcJtOm@dAKr@lAdB|AxB_A`E{@pDoBjJk@rCYz@mBvDUr@Kt@ApAFv@Pt@lApEf@nBHb@FtACrAObA";
        points = PolylineDecoder.decodePolyline(input);
        for (LatLng point: points) {
            rideNodeList.add(new RideNode(point.latitude, point.longitude, 2, counter++));
        }
        counter = 0;
        input = "_pmoGvbccK]Pq@sEy@qFq@}DSc@S_@w@kAKUMq@sCnAiJzDuB|@yDzAgBr@kDzAgCfAwBz@QJm@wBwA_DoBiDk@mAu@uBUs@Ow@WuAW{A@m@DWDKD]AQGOKMGCKAMDIJGJU^qAzBgBtC}BoEuCcFw@wAuAwB_CyD}AwCmAiCQg@EU?YJm@N_@zAgB~AcBPQ\\\\w@Vk@^sA|@wCTq@\\\\u@l@i@lCoAd@U";
        points = PolylineDecoder.decodePolyline(input);
        for (LatLng point: points) {
            rideNodeList.add(new RideNode(point.latitude, point.longitude, 3, counter++));
        }
        counter = 0;
        input = "_pmoGvbccK]Pb@lCj@jDfArHzAxJZzByDxA_FfBcFxBlBzKx@hEW^s@jAs@nAUf@q@|Ac@v@]d@[VsB`AgEnBa@Xb@zBpDlPrA`GHh@z@fD";
        points = PolylineDecoder.decodePolyline(input);
        for (LatLng point: points) {
            rideNodeList.add(new RideNode(point.latitude, point.longitude, 4, counter++));
        }
        counter = 0;
        input = "kunoGjvdcK_@oBm@_DYqBg@iBSgAm@cD{@_F}@cFd@I`@OdAe@pCoAlDwAlEeBrAi@W_BWwAfBo@hDsA|HcDdGiCrFyBOaA";
        points = PolylineDecoder.decodePolyline(input);
        for (LatLng point: points) {
            rideNodeList.add(new RideNode(point.latitude, point.longitude, 6, counter++));
        }
        //44.643398972542684, -63.58923069048749
        //44.64500128237824, -63.599174152177845
        //44.6398877597073, -63.58913143096513
        //RideRequestNode startNode = new RideRequestNode(44.6398877597073, -63.58913143096513, 1);
        //RideRequestNode endNode = new RideRequestNode(44.64500128237824, -63.599174152177845, 1);
        RideRequestNode endNode = new RideRequestNode(44.6398877597073, -63.58913143096513, 1);
        RideRequestNode startNode = new RideRequestNode(44.64500128237824, -63.599174152177845, 1);
        Set<RideNode> validRidesForStartNode = new HashSet<>();
        Map<RideNode, RideNode> validRidesForEndNode = new HashMap<>();

        double threshold = .5;
        double a = System.currentTimeMillis();
        for (RideNode rideNode: rideNodeList) {
            double distanceFromStartNode = DistanceFinder.findDistance(startNode.latitude, rideNode.latitude,
                    startNode.longitude, rideNode.longitude);
            System.out.println("distance from ride request start node for ride : " + rideNode.rideId  + " : " + distanceFromStartNode);
            System.out.println("distance : " + distanceFromStartNode);
            if (distanceFromStartNode < threshold && !validRidesForStartNode.contains(rideNode)) {
                validRidesForStartNode.add(rideNode);
            }
            double distanceFromEndNode = DistanceFinder.findDistance(endNode.latitude, rideNode.latitude,
                    endNode.longitude, rideNode.longitude);
            System.out.println("distance from ride request end node " + distanceFromEndNode);
            System.out.println("distance : " + distanceFromEndNode);
            System.out.println("Latitude : " + rideNode.latitude + "longitude : " + rideNode.longitude);
            if (distanceFromEndNode <= threshold && !validRidesForEndNode.containsKey(rideNode)) {
                validRidesForEndNode.put(rideNode, rideNode);
            }
        }
        validRidesForStartNode.retainAll(validRidesForEndNode.keySet());
        System.out.println("************");
        for (RideNode rideNode: validRidesForStartNode) {
            System.out.println(rideNode);
        }
        List<RideNode> resultRides = new ArrayList<>();
        for (RideNode rideNode: validRidesForStartNode) {
            RideNode temp = validRidesForEndNode.get(rideNode);
            if (rideNode.sequence < temp.sequence) {
                resultRides.add(rideNode);
            }
        }
        System.out.println(resultRides);
        //Collections.sort(rideNodeList);
        double b = System.currentTimeMillis();
        System.out.println("time taken : " + (b - a)/100);
    }
}
