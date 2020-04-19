/*
 *   Copyright [2020] [Harry0198]
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.haroldstudios.mailme.components.hooks;

import com.haroldstudios.mailme.MailMe;
import com.haroldstudios.mailme.utility.Locale;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

import java.util.logging.Level;

public final class VaultHook {

    private static Economy econ = null;
    private final MailMe plugin;
    private final double cost;

    public VaultHook(final MailMe plugin) {
        this.plugin = plugin;
        cost = plugin.getConfig().getDouble("cost");
        if (!plugin.getConfig().getBoolean("enable-vault")) return;

        RegisteredServiceProvider<Economy> rsp = plugin.getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            plugin.getLogger().log(Level.WARNING, "No Vault Economy Plugin found!");
            return;
        }
        econ = rsp.getProvider();
    }

    /**
     * Attempts to use economy for sending mail
     * @param player Player to attempt with
     * @return If transaction was success
     */
    public boolean attemptTransaction(Player player) {
        if (econ == null) return true;
        Locale.LANG lang = plugin.getDataStoreHandler().getPlayerData(player).getLang();
        EconomyResponse r = econ.withdrawPlayer(player, cost);
        if(!r.transactionSuccess()) {
            player.sendMessage(plugin.getLocale().getMessage(lang, "cmd.vault-insufficient-funds"));
            return false;
        }
        return true;
    }
}
