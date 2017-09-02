/*
 * Copyright 2017 Malanius
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package entities.battlestar;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class LayerTest {

    private Layer testLayer;
    private int maxLevel = 5;
    private int baseUpgradeCost = 2_000;
    private int baseHitPoints = 1_000;

    @Before
    public void setUp() throws Exception {
        testLayer = new Layer("Hull", maxLevel, baseUpgradeCost, baseHitPoints);
    }

    @After
    public void tearDown() throws Exception {
        testLayer = null;
    }

    @Test
    public void getHitPoints() throws Exception {
        Assert.assertEquals("getHitPoints() not equal to constructor base hit points!", baseHitPoints, testLayer.getHitPoints());
    }

    @Test
    public void getMaxHitPoints() throws Exception {
        Assert.assertEquals("getMaxHitPoints() not equal to constructor hit points!", testLayer.getMaxHitPoints(), baseHitPoints);
        testLayer.upgrade();
        Assert.assertEquals("getMaxHitPoints() not equal to +1 level hit points!", baseHitPoints * 2, testLayer.getMaxHitPoints());
        testLayer.upgrade();
        Assert.assertEquals("getMaxHitPoints() not equal to +2 level hit points!", baseHitPoints * 3, testLayer.getMaxHitPoints());
        testLayer.upgrade();
        Assert.assertEquals("getMaxHitPoints() not equal to +3 level hit points!", baseHitPoints * 4, testLayer.getMaxHitPoints());
        testLayer.upgrade();
        Assert.assertEquals("getMaxHitPoints() not equal to +5 level hit points!", baseHitPoints * 5, testLayer.getMaxHitPoints());
    }

    @Test
    public void looseHitPoints() throws Exception {
        testLayer.looseHitPoints(0);
        Assert.assertEquals("getHitPoints() not equal to -0 damage!", baseHitPoints, testLayer.getHitPoints());
        testLayer.looseHitPoints(450);
        Assert.assertEquals("getHitPoints() not equal to -450 damage!", baseHitPoints - 450, testLayer.getHitPoints());
        testLayer.looseHitPoints(99_000);
        Assert.assertEquals("getHitPoints() not equal to 0 after 99k damage!", 0, testLayer.getHitPoints());
    }

    @Test
    public void upgrade() throws Exception {
        Assert.assertEquals("Upgrade to lvl 2 not successful!", true, testLayer.upgrade());
        Assert.assertEquals("getHitPoints() not equal to +1 level hit points!", baseHitPoints * 2, testLayer.getHitPoints());
        Assert.assertEquals("Upgrade to lvl 3 not successful!", true, testLayer.upgrade());
        Assert.assertEquals("getHitPoints() not equal to +2 level hit points!", baseHitPoints * 3, testLayer.getHitPoints());
        Assert.assertEquals("Upgrade to lvl 4 not successful!", true, testLayer.upgrade());
        Assert.assertEquals("getHitPoints() not equal to +3 level hit points!", baseHitPoints * 4, testLayer.getHitPoints());
        testLayer.looseHitPoints(750); //Layer should be repaired during upgrade
        Assert.assertEquals("Upgrade to lvl 5 not successful!", true, testLayer.upgrade());
        Assert.assertEquals("getHitPoints() not equal to +5 level hit points!", baseHitPoints * 5, testLayer.getHitPoints());
        Assert.assertEquals("Upgrade beyond max level should not be successful!", false, testLayer.upgrade());
    }

    @Test
    public void repair() throws Exception {
        Assert.assertEquals("Repair of layer with no damage should not be successful!", false, testLayer.repair());
        testLayer.looseHitPoints(250);
        Assert.assertEquals("Repair of layer with  damage should be successful!", true, testLayer.repair());
        Assert.assertEquals("Repaired layer should have maximum possible HP!", testLayer.getMaxHitPoints(), testLayer.getHitPoints());
    }

    @Test
    public void getRepairCost() {
        Assert.assertEquals("Repair cost of undamaged layer should be 0!", 0, testLayer.getRepairCost());
        testLayer.looseHitPoints(250);
        Assert.assertEquals("Repair cost of damaged layer by 250 HP should be 250!", 250, testLayer.getRepairCost());
    }

    @Test
    public void getUpgradeCost() {
        Assert.assertEquals("Upgrade cost to lvl 2 should be 2 * baseUpgradeCost!", baseUpgradeCost * 2, testLayer.getUpgradeCost());
        testLayer.upgrade();
        Assert.assertEquals("Upgrade cost to lvl 3 should be 3 * baseUpgradeCost!", baseUpgradeCost * 3, testLayer.getUpgradeCost());
        testLayer.upgrade();
        Assert.assertEquals("Upgrade cost to lvl 4 should be 4 * baseUpgradeCost!", baseUpgradeCost * 4, testLayer.getUpgradeCost());
        testLayer.upgrade();
        testLayer.looseHitPoints(777);
        Assert.assertEquals("Upgrade cost to lvl 5  with damaged layer should be 5 * baseUpgradeCost + damage!", baseUpgradeCost * 5 + 777, testLayer.getUpgradeCost());
        testLayer.upgrade();
        Assert.assertEquals("Upgrade cost beyond max level should be 0", 0, testLayer.getUpgradeCost());
    }

}