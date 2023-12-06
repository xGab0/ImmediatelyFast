/*
 * This file is part of ImmediatelyFast - https://github.com/RaphiMC/ImmediatelyFast
 * Copyright (C) 2023 RK_01/RaphiMC and contributors
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package net.raphimc.immediatelyfast.injection.mixins.hud_batching;

import net.minecraft.client.gui.screen.ChatInputSuggestor;
import net.raphimc.immediatelyfast.feature.batching.BatchingBuffers;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = ChatInputSuggestor.class, priority = 1500)
public abstract class MixinChatInputSuggestor {

    @Inject(method = "render", at = @At("HEAD"))
    private void beginBatching(CallbackInfo ci) {
        if (BatchingBuffers.isHudBatching()) {
            throw new IllegalStateException("Already batching");
        }

        BatchingBuffers.beginHudBatching();
    }

    @Inject(method = "render", at = @At("RETURN"))
    private void endBatching(CallbackInfo ci) {
        if (!BatchingBuffers.isHudBatching()) {
            throw new IllegalStateException("Not batching");
        }

        BatchingBuffers.endHudBatching();
    }

}
