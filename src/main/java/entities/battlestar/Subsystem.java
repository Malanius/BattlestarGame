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
 * Abstract class for Battlestars' internal systems.
 *
 * @author Malanius
 * @version 1.0.0
 * @since 1.0.0
 */
abstract class Subsystem {

    private static final Logger LOG = LoggerFactory.getLogger(Subsystem.class);

    private final String name;
    private int level;
    private final int maxLevel;
    private int baseUpgradeCost;

    /**
     * Creates the Battlestar's internal component.
     *
     * @param name            of the subsystem
     * @param baseUpgradeCost basic cost for the upgrade to next level
     * @param maxLevel        maximum level the subsystem can have
     */
    Subsystem(String name, int maxLevel, int baseUpgradeCost) {
        this.name = name;
        this.level = 1;
        this.baseUpgradeCost = baseUpgradeCost;
        this.maxLevel = maxLevel;
    }

    int getLevel() {
        return level;
    }

    int getBaseUpgradeCost() {
        return baseUpgradeCost;
    }

    int getMaxLevel() {
        return maxLevel;
    }

    /**
     * Upgrades the subsystem to next level, if not already at max.
     *
     * @return true/false success.
     */
    boolean upgrade() {
        if (level < maxLevel) {
            level++;
            return true;
        }
        LOG.warn("{} already at max level, can't upgrade further!", name);
        return false;
    }
}
