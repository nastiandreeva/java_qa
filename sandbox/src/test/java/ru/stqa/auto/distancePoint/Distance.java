package ru.stqa.auto.distancePoint;

import org.testng.Assert;
import org.testng.annotations.Test;

public class Distance {

    @Test
    public void testDistanceMethod() {
      Point p1 = new Point(0, 1);
      Point p2 = new Point(2, -2);
      Assert.assertEquals(p1.distanceMethod(p2), 3.605551275463989);
    }

  }
