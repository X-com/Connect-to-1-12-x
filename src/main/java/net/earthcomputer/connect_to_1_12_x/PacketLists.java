package net.earthcomputer.connect_to_1_12_x;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.network.EnumConnectionState;
import net.minecraft.network.EnumPacketDirection;
import net.minecraft.network.play.client.CPacketAnimation;
import net.minecraft.network.play.client.CPacketChatMessage;
import net.minecraft.network.play.client.CPacketClickWindow;
import net.minecraft.network.play.client.CPacketClientSettings;
import net.minecraft.network.play.client.CPacketClientStatus;
import net.minecraft.network.play.client.CPacketCloseWindow;
import net.minecraft.network.play.client.CPacketConfirmTeleport;
import net.minecraft.network.play.client.CPacketConfirmTransaction;
import net.minecraft.network.play.client.CPacketCreativeInventoryAction;
import net.minecraft.network.play.client.CPacketCustomPayload;
import net.minecraft.network.play.client.CPacketEnchantItem;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.network.play.client.CPacketInput;
import net.minecraft.network.play.client.CPacketKeepAlive;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketPlayerAbilities;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.network.play.client.CPacketRecipeInfo;
import net.minecraft.network.play.client.CPacketRecipePlacement;
import net.minecraft.network.play.client.CPacketResourcePackStatus;
import net.minecraft.network.play.client.CPacketSeenAdvancements;
import net.minecraft.network.play.client.CPacketSpectate;
import net.minecraft.network.play.client.CPacketSteerBoat;
import net.minecraft.network.play.client.CPacketTabComplete;
import net.minecraft.network.play.client.CPacketUpdateSign;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.network.play.client.CPacketVehicleMove;
import net.minecraft.network.play.server.SPacketAdvancementInfo;
import net.minecraft.network.play.server.SPacketAnimation;
import net.minecraft.network.play.server.SPacketBlockAction;
import net.minecraft.network.play.server.SPacketBlockBreakAnim;
import net.minecraft.network.play.server.SPacketBlockChange;
import net.minecraft.network.play.server.SPacketCamera;
import net.minecraft.network.play.server.SPacketChangeGameState;
import net.minecraft.network.play.server.SPacketChat;
import net.minecraft.network.play.server.SPacketChunkData;
import net.minecraft.network.play.server.SPacketCloseWindow;
import net.minecraft.network.play.server.SPacketCollectItem;
import net.minecraft.network.play.server.SPacketCombatEvent;
import net.minecraft.network.play.server.SPacketConfirmTransaction;
import net.minecraft.network.play.server.SPacketCooldown;
import net.minecraft.network.play.server.SPacketCustomPayload;
import net.minecraft.network.play.server.SPacketCustomSound;
import net.minecraft.network.play.server.SPacketDestroyEntities;
import net.minecraft.network.play.server.SPacketDisconnect;
import net.minecraft.network.play.server.SPacketDisplayObjective;
import net.minecraft.network.play.server.SPacketEffect;
import net.minecraft.network.play.server.SPacketEntity;
import net.minecraft.network.play.server.SPacketEntityAttach;
import net.minecraft.network.play.server.SPacketEntityEffect;
import net.minecraft.network.play.server.SPacketEntityEquipment;
import net.minecraft.network.play.server.SPacketEntityHeadLook;
import net.minecraft.network.play.server.SPacketEntityMetadata;
import net.minecraft.network.play.server.SPacketEntityProperties;
import net.minecraft.network.play.server.SPacketEntityStatus;
import net.minecraft.network.play.server.SPacketEntityTeleport;
import net.minecraft.network.play.server.SPacketEntityVelocity;
import net.minecraft.network.play.server.SPacketExplosion;
import net.minecraft.network.play.server.SPacketHeldItemChange;
import net.minecraft.network.play.server.SPacketJoinGame;
import net.minecraft.network.play.server.SPacketKeepAlive;
import net.minecraft.network.play.server.SPacketMaps;
import net.minecraft.network.play.server.SPacketMoveVehicle;
import net.minecraft.network.play.server.SPacketMultiBlockChange;
import net.minecraft.network.play.server.SPacketOpenWindow;
import net.minecraft.network.play.server.SPacketParticles;
import net.minecraft.network.play.server.SPacketPlayerAbilities;
import net.minecraft.network.play.server.SPacketPlayerListHeaderFooter;
import net.minecraft.network.play.server.SPacketPlayerListItem;
import net.minecraft.network.play.server.SPacketPlayerPosLook;
import net.minecraft.network.play.server.SPacketRecipeBook;
import net.minecraft.network.play.server.SPacketRemoveEntityEffect;
import net.minecraft.network.play.server.SPacketResourcePackSend;
import net.minecraft.network.play.server.SPacketRespawn;
import net.minecraft.network.play.server.SPacketScoreboardObjective;
import net.minecraft.network.play.server.SPacketSelectAdvancementsTab;
import net.minecraft.network.play.server.SPacketServerDifficulty;
import net.minecraft.network.play.server.SPacketSetExperience;
import net.minecraft.network.play.server.SPacketSetPassengers;
import net.minecraft.network.play.server.SPacketSetSlot;
import net.minecraft.network.play.server.SPacketSignEditorOpen;
import net.minecraft.network.play.server.SPacketSoundEffect;
import net.minecraft.network.play.server.SPacketSpawnExperienceOrb;
import net.minecraft.network.play.server.SPacketSpawnGlobalEntity;
import net.minecraft.network.play.server.SPacketSpawnMob;
import net.minecraft.network.play.server.SPacketSpawnObject;
import net.minecraft.network.play.server.SPacketSpawnPainting;
import net.minecraft.network.play.server.SPacketSpawnPlayer;
import net.minecraft.network.play.server.SPacketSpawnPosition;
import net.minecraft.network.play.server.SPacketStatistics;
import net.minecraft.network.play.server.SPacketTabComplete;
import net.minecraft.network.play.server.SPacketTeams;
import net.minecraft.network.play.server.SPacketTimeUpdate;
import net.minecraft.network.play.server.SPacketTitle;
import net.minecraft.network.play.server.SPacketUnloadChunk;
import net.minecraft.network.play.server.SPacketUpdateBossInfo;
import net.minecraft.network.play.server.SPacketUpdateHealth;
import net.minecraft.network.play.server.SPacketUpdateScore;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.network.play.server.SPacketUseBed;
import net.minecraft.network.play.server.SPacketWindowItems;
import net.minecraft.network.play.server.SPacketWindowProperty;
import net.minecraft.network.play.server.SPacketWorldBorder;

public class PacketLists {

	private static final Logger LOGGER = LogManager.getLogger("Connect to 1.12.x");

	private static final Map<Integer, Consumer<IPacketRegistry>> PROTOCOL_SWITCHERS = new LinkedHashMap<>();

	public static final int PROTOCOL_1_12 = 335;
	public static final int PROTOCOL_1_12_1 = 338;
	public static final int PROTOCOL_1_12_2 = 340;

	static {
		registerProtocol(PROTOCOL_1_12, PacketLists::register112PlayPackets);
		registerProtocol(PROTOCOL_1_12_1, PacketLists::register1121PlayPackets);
		registerProtocol(PROTOCOL_1_12_2, PacketLists::register1122PlayPackets);
	}

	private static void registerProtocol(int version, Consumer<IPacketRegistry> packetRegisterer) {
		PROTOCOL_SWITCHERS.put(version, packetRegisterer);
	}

	public static boolean isProtocolSupported(int version) {
		return PROTOCOL_SWITCHERS.containsKey(version);
	}

	public static Set<Integer> getSupportedProtocols() {
		return PROTOCOL_SWITCHERS.keySet();
	}

	public static void switchProtocol(int version) {
		LOGGER.info("Switching to protocol version " + version);
		PROTOCOL_SWITCHERS.get(version).accept((IPacketRegistry) EnumConnectionState.PLAY);
	}

	public static void register112PlayPackets(IPacketRegistry registry) {
		registry.clear();
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketSpawnObject.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketSpawnExperienceOrb.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketSpawnGlobalEntity.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketSpawnMob.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketSpawnPainting.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketSpawnPlayer.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketAnimation.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketStatistics.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketBlockBreakAnim.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketUpdateTileEntity.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketBlockAction.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketBlockChange.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketUpdateBossInfo.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketServerDifficulty.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketTabComplete.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketChat.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketMultiBlockChange.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketConfirmTransaction.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketCloseWindow.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketOpenWindow.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketWindowItems.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketWindowProperty.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketSetSlot.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketCooldown.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketCustomPayload.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketCustomSound.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketDisconnect.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketEntityStatus.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketExplosion.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketUnloadChunk.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketChangeGameState.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketKeepAlive.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketChunkData.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketEffect.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketParticles.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketJoinGame.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketMaps.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketEntity.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketEntity.S15PacketEntityRelMove.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketEntity.S17PacketEntityLookMove.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketEntity.S16PacketEntityLook.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketMoveVehicle.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketSignEditorOpen.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketPlayerAbilities.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketCombatEvent.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketPlayerListItem.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketPlayerPosLook.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketUseBed.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketRecipeBook.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketDestroyEntities.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketRemoveEntityEffect.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketResourcePackSend.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketRespawn.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketEntityHeadLook.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketSelectAdvancementsTab.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketWorldBorder.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketCamera.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketHeldItemChange.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketDisplayObjective.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketEntityMetadata.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketEntityAttach.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketEntityVelocity.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketEntityEquipment.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketSetExperience.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketUpdateHealth.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketScoreboardObjective.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketSetPassengers.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketTeams.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketUpdateScore.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketSpawnPosition.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketTimeUpdate.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketTitle.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketSoundEffect.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketPlayerListHeaderFooter.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketCollectItem.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketEntityTeleport.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketAdvancementInfo.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketEntityProperties.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketEntityEffect.class);
		registry.register(EnumPacketDirection.SERVERBOUND, CPacketConfirmTeleport.class);
		registry.register(EnumPacketDirection.SERVERBOUND, CPacketRecipePlacement.class);
		registry.register(EnumPacketDirection.SERVERBOUND, CPacketTabComplete.class);
		registry.register(EnumPacketDirection.SERVERBOUND, CPacketChatMessage.class);
		registry.register(EnumPacketDirection.SERVERBOUND, CPacketClientStatus.class);
		registry.register(EnumPacketDirection.SERVERBOUND, CPacketClientSettings.class);
		registry.register(EnumPacketDirection.SERVERBOUND, CPacketConfirmTransaction.class);
		registry.register(EnumPacketDirection.SERVERBOUND, CPacketEnchantItem.class);
		registry.register(EnumPacketDirection.SERVERBOUND, CPacketClickWindow.class);
		registry.register(EnumPacketDirection.SERVERBOUND, CPacketCloseWindow.class);
		registry.register(EnumPacketDirection.SERVERBOUND, CPacketCustomPayload.class);
		registry.register(EnumPacketDirection.SERVERBOUND, CPacketUseEntity.class);
		registry.register(EnumPacketDirection.SERVERBOUND, CPacketKeepAlive.class);
		registry.register(EnumPacketDirection.SERVERBOUND, CPacketPlayer.class);
		registry.register(EnumPacketDirection.SERVERBOUND, CPacketPlayer.Position.class);
		registry.register(EnumPacketDirection.SERVERBOUND, CPacketPlayer.PositionRotation.class);
		registry.register(EnumPacketDirection.SERVERBOUND, CPacketPlayer.Rotation.class);
		registry.register(EnumPacketDirection.SERVERBOUND, CPacketVehicleMove.class);
		registry.register(EnumPacketDirection.SERVERBOUND, CPacketSteerBoat.class);
		registry.register(EnumPacketDirection.SERVERBOUND, CPacketPlayerAbilities.class);
		registry.register(EnumPacketDirection.SERVERBOUND, CPacketPlayerDigging.class);
		registry.register(EnumPacketDirection.SERVERBOUND, CPacketEntityAction.class);
		registry.register(EnumPacketDirection.SERVERBOUND, CPacketInput.class);
		registry.register(EnumPacketDirection.SERVERBOUND, CPacketRecipeInfo.class);
		registry.register(EnumPacketDirection.SERVERBOUND, CPacketResourcePackStatus.class);
		registry.register(EnumPacketDirection.SERVERBOUND, CPacketSeenAdvancements.class);
		registry.register(EnumPacketDirection.SERVERBOUND, CPacketHeldItemChange.class);
		registry.register(EnumPacketDirection.SERVERBOUND, CPacketCreativeInventoryAction.class);
		registry.register(EnumPacketDirection.SERVERBOUND, CPacketUpdateSign.class);
		registry.register(EnumPacketDirection.SERVERBOUND, CPacketAnimation.class);
		registry.register(EnumPacketDirection.SERVERBOUND, CPacketSpectate.class);
		registry.register(EnumPacketDirection.SERVERBOUND, CPacketPlayerTryUseItemOnBlock.class);
		registry.register(EnumPacketDirection.SERVERBOUND, CPacketPlayerTryUseItem.class);
	}

	public static void register1121PlayPackets(IPacketRegistry registry) {
		registry.clear();
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketSpawnObject.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketSpawnExperienceOrb.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketSpawnGlobalEntity.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketSpawnMob.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketSpawnPainting.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketSpawnPlayer.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketAnimation.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketStatistics.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketBlockBreakAnim.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketUpdateTileEntity.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketBlockAction.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketBlockChange.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketUpdateBossInfo.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketServerDifficulty.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketTabComplete.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketChat.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketMultiBlockChange.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketConfirmTransaction.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketCloseWindow.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketOpenWindow.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketWindowItems.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketWindowProperty.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketSetSlot.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketCooldown.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketCustomPayload.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketCustomSound.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketDisconnect.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketEntityStatus.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketExplosion.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketUnloadChunk.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketChangeGameState.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketKeepAlive.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketChunkData.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketEffect.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketParticles.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketJoinGame.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketMaps.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketEntity.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketEntity.S15PacketEntityRelMove.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketEntity.S17PacketEntityLookMove.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketEntity.S16PacketEntityLook.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketMoveVehicle.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketSignEditorOpen.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketPlaceGhostRecipe.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketPlayerAbilities.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketCombatEvent.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketPlayerListItem.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketPlayerPosLook.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketUseBed.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketRecipeBook.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketDestroyEntities.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketRemoveEntityEffect.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketResourcePackSend.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketRespawn.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketEntityHeadLook.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketSelectAdvancementsTab.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketWorldBorder.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketCamera.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketHeldItemChange.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketDisplayObjective.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketEntityMetadata.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketEntityAttach.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketEntityVelocity.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketEntityEquipment.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketSetExperience.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketUpdateHealth.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketScoreboardObjective.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketSetPassengers.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketTeams.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketUpdateScore.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketSpawnPosition.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketTimeUpdate.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketTitle.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketSoundEffect.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketPlayerListHeaderFooter.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketCollectItem.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketEntityTeleport.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketAdvancementInfo.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketEntityProperties.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketEntityEffect.class);
		registry.register(EnumPacketDirection.SERVERBOUND, CPacketConfirmTeleport.class);
		registry.register(EnumPacketDirection.SERVERBOUND, CPacketTabComplete.class);
		registry.register(EnumPacketDirection.SERVERBOUND, CPacketChatMessage.class);
		registry.register(EnumPacketDirection.SERVERBOUND, CPacketClientStatus.class);
		registry.register(EnumPacketDirection.SERVERBOUND, CPacketClientSettings.class);
		registry.register(EnumPacketDirection.SERVERBOUND, CPacketConfirmTransaction.class);
		registry.register(EnumPacketDirection.SERVERBOUND, CPacketEnchantItem.class);
		registry.register(EnumPacketDirection.SERVERBOUND, CPacketClickWindow.class);
		registry.register(EnumPacketDirection.SERVERBOUND, CPacketCloseWindow.class);
		registry.register(EnumPacketDirection.SERVERBOUND, CPacketCustomPayload.class);
		registry.register(EnumPacketDirection.SERVERBOUND, CPacketUseEntity.class);
		registry.register(EnumPacketDirection.SERVERBOUND, CPacketKeepAlive.class);
		registry.register(EnumPacketDirection.SERVERBOUND, CPacketPlayer.class);
		registry.register(EnumPacketDirection.SERVERBOUND, CPacketPlayer.Position.class);
		registry.register(EnumPacketDirection.SERVERBOUND, CPacketPlayer.PositionRotation.class);
		registry.register(EnumPacketDirection.SERVERBOUND, CPacketPlayer.Rotation.class);
		registry.register(EnumPacketDirection.SERVERBOUND, CPacketVehicleMove.class);
		registry.register(EnumPacketDirection.SERVERBOUND, CPacketSteerBoat.class);
		registry.register(EnumPacketDirection.SERVERBOUND, CPacketPlaceRecipe.class);
		registry.register(EnumPacketDirection.SERVERBOUND, CPacketPlayerAbilities.class);
		registry.register(EnumPacketDirection.SERVERBOUND, CPacketPlayerDigging.class);
		registry.register(EnumPacketDirection.SERVERBOUND, CPacketEntityAction.class);
		registry.register(EnumPacketDirection.SERVERBOUND, CPacketInput.class);
		registry.register(EnumPacketDirection.SERVERBOUND, CPacketRecipeInfo.class);
		registry.register(EnumPacketDirection.SERVERBOUND, CPacketResourcePackStatus.class);
		registry.register(EnumPacketDirection.SERVERBOUND, CPacketSeenAdvancements.class);
		registry.register(EnumPacketDirection.SERVERBOUND, CPacketHeldItemChange.class);
		registry.register(EnumPacketDirection.SERVERBOUND, CPacketCreativeInventoryAction.class);
		registry.register(EnumPacketDirection.SERVERBOUND, CPacketUpdateSign.class);
		registry.register(EnumPacketDirection.SERVERBOUND, CPacketAnimation.class);
		registry.register(EnumPacketDirection.SERVERBOUND, CPacketSpectate.class);
		registry.register(EnumPacketDirection.SERVERBOUND, CPacketPlayerTryUseItemOnBlock.class);
		registry.register(EnumPacketDirection.SERVERBOUND, CPacketPlayerTryUseItem.class);
	}

	public static void register1122PlayPackets(IPacketRegistry registry) {
		registry.clear();
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketSpawnObject.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketSpawnExperienceOrb.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketSpawnGlobalEntity.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketSpawnMob.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketSpawnPainting.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketSpawnPlayer.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketAnimation.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketStatistics.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketBlockBreakAnim.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketUpdateTileEntity.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketBlockAction.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketBlockChange.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketUpdateBossInfo.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketServerDifficulty.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketTabComplete.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketChat.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketMultiBlockChange.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketConfirmTransaction.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketCloseWindow.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketOpenWindow.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketWindowItems.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketWindowProperty.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketSetSlot.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketCooldown.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketCustomPayload.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketCustomSound.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketDisconnect.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketEntityStatus.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketExplosion.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketUnloadChunk.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketChangeGameState.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketKeepAlive1122.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketChunkData.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketEffect.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketParticles.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketJoinGame.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketMaps.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketEntity.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketEntity.S15PacketEntityRelMove.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketEntity.S17PacketEntityLookMove.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketEntity.S16PacketEntityLook.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketMoveVehicle.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketSignEditorOpen.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketPlaceGhostRecipe.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketPlayerAbilities.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketCombatEvent.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketPlayerListItem.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketPlayerPosLook.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketUseBed.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketRecipeBook.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketDestroyEntities.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketRemoveEntityEffect.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketResourcePackSend.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketRespawn.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketEntityHeadLook.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketSelectAdvancementsTab.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketWorldBorder.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketCamera.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketHeldItemChange.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketDisplayObjective.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketEntityMetadata.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketEntityAttach.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketEntityVelocity.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketEntityEquipment.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketSetExperience.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketUpdateHealth.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketScoreboardObjective.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketSetPassengers.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketTeams.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketUpdateScore.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketSpawnPosition.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketTimeUpdate.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketTitle.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketSoundEffect.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketPlayerListHeaderFooter.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketCollectItem.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketEntityTeleport.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketAdvancementInfo.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketEntityProperties.class);
		registry.register(EnumPacketDirection.CLIENTBOUND, SPacketEntityEffect.class);
		registry.register(EnumPacketDirection.SERVERBOUND, CPacketConfirmTeleport.class);
		registry.register(EnumPacketDirection.SERVERBOUND, CPacketTabComplete.class);
		registry.register(EnumPacketDirection.SERVERBOUND, CPacketChatMessage.class);
		registry.register(EnumPacketDirection.SERVERBOUND, CPacketClientStatus.class);
		registry.register(EnumPacketDirection.SERVERBOUND, CPacketClientSettings.class);
		registry.register(EnumPacketDirection.SERVERBOUND, CPacketConfirmTransaction.class);
		registry.register(EnumPacketDirection.SERVERBOUND, CPacketEnchantItem.class);
		registry.register(EnumPacketDirection.SERVERBOUND, CPacketClickWindow.class);
		registry.register(EnumPacketDirection.SERVERBOUND, CPacketCloseWindow.class);
		registry.register(EnumPacketDirection.SERVERBOUND, CPacketCustomPayload.class);
		registry.register(EnumPacketDirection.SERVERBOUND, CPacketUseEntity.class);
		registry.register(EnumPacketDirection.SERVERBOUND, CPacketKeepAlive1122.class);
		registry.register(EnumPacketDirection.SERVERBOUND, CPacketPlayer.class);
		registry.register(EnumPacketDirection.SERVERBOUND, CPacketPlayer.Position.class);
		registry.register(EnumPacketDirection.SERVERBOUND, CPacketPlayer.PositionRotation.class);
		registry.register(EnumPacketDirection.SERVERBOUND, CPacketPlayer.Rotation.class);
		registry.register(EnumPacketDirection.SERVERBOUND, CPacketVehicleMove.class);
		registry.register(EnumPacketDirection.SERVERBOUND, CPacketSteerBoat.class);
		registry.register(EnumPacketDirection.SERVERBOUND, CPacketPlaceRecipe.class);
		registry.register(EnumPacketDirection.SERVERBOUND, CPacketPlayerAbilities.class);
		registry.register(EnumPacketDirection.SERVERBOUND, CPacketPlayerDigging.class);
		registry.register(EnumPacketDirection.SERVERBOUND, CPacketEntityAction.class);
		registry.register(EnumPacketDirection.SERVERBOUND, CPacketInput.class);
		registry.register(EnumPacketDirection.SERVERBOUND, CPacketRecipeInfo.class);
		registry.register(EnumPacketDirection.SERVERBOUND, CPacketResourcePackStatus.class);
		registry.register(EnumPacketDirection.SERVERBOUND, CPacketSeenAdvancements.class);
		registry.register(EnumPacketDirection.SERVERBOUND, CPacketHeldItemChange.class);
		registry.register(EnumPacketDirection.SERVERBOUND, CPacketCreativeInventoryAction.class);
		registry.register(EnumPacketDirection.SERVERBOUND, CPacketUpdateSign.class);
		registry.register(EnumPacketDirection.SERVERBOUND, CPacketAnimation.class);
		registry.register(EnumPacketDirection.SERVERBOUND, CPacketSpectate.class);
		registry.register(EnumPacketDirection.SERVERBOUND, CPacketPlayerTryUseItemOnBlock.class);
		registry.register(EnumPacketDirection.SERVERBOUND, CPacketPlayerTryUseItem.class);
	}

}
