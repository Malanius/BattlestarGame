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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Represents the Battlestars' protection layers - hull and armor.
 *
 * @author Malanius
 * @version 1.0.0
 * @since 1.0.0
 */
class Layer extends Subsystem {

    private static final Logger LOG = LoggerFactory.getLogger(Subsystem.class);

    private int hitPoints;
    private final int baseHitPoints;
    private int maxHitPoints;

    Layer(String name, int maxLevel, int baseUpgradeCost, int baseHitPoints) {
        super(name, maxLevel, baseUpgradeCost);
        this.baseHitPoints = baseHitPoints;
        this.maxHitPoints = this.baseHitPoints * getLevel();
        this.hitPoints = maxHitPoints;
        LOG.debug("{} name created.", name);
    }

    int getHitPoints() {
        return hitPoints;
    }

    int getMaxHitPoints() {
        return maxHitPoints;
    }

    int looseHitPoints(int damage) {
        hitPoints -= damage;
        if (hitPoints < 0) hitPoints = 0;
        return hitPoints;
    }

    /**
     * Upgrades the hull level and maximum hit points.
     * Upgrade can't be done if already at maximum level.
     * Layer is repaired in the process of upgrade.
     *
     * @return true if successful.
     */
    @Override
    boolean upgrade() {
        if (super.upgrade()) {
            this.maxHitPoints = baseHitPoints * getLevel();
            hitPoints = maxHitPoints;
            return true;
        }
        return false;
    }

    int getUpgradeCost() {
        if (getLevel() < getMaxLevel()) {
            return (getBaseUpgradeCost() * (getLevel() + 1)) + getRepairCost();
        }
        return 0;
    }

    /**
     * Fully repairs the damage to the layer, if damaged.
     *
     * @return true if repaired, false if no repair is needed.
     */
    boolean repair() {
        if (hitPoints < maxHitPoints) {
            hitPoints = maxHitPoints;
            return true;
        }
        return false;
    }

    int getRepairCost() {
        return maxHitPoints - hitPoints;
    }
}
