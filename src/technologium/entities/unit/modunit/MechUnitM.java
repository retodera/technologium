package technologium.entities.unit.modunit;

import arc.func.Cons;
import arc.graphics.Color;
import arc.graphics.g2d.TextureRegion;
import arc.math.geom.*;
import arc.scene.ui.layout.Table;
import arc.struct.*;
import arc.util.io.*;
import mindustry.ai.types.CommandAI;
import mindustry.async.PhysicsProcess;
import mindustry.ctype.*;
import mindustry.entities.EntityCollisions;
import mindustry.entities.abilities.Ability;
import mindustry.entities.units.*;
import mindustry.game.Team;
import mindustry.gen.*;
import mindustry.graphics.Trail;
import mindustry.logic.LAccess;
import mindustry.type.*;
import mindustry.world.*;
import mindustry.world.blocks.environment.Floor;
import mindustry.world.blocks.storage.CoreBlock;
import technologium.entities.unit.*;

import java.nio.FloatBuffer;
import java.util.HashMap;

//TODO implement other modifiers
public class MechUnitM extends MechUnit implements UnitModification {
    private final MechUnit reference;
    public HashMap<ModificatorType, FloatSeq> modifiers = new HashMap<>(ModificatorType.total());
    @Override
    public HashMap<ModificatorType, FloatSeq> getModifiers() {
        return modifiers;
    }
    
    public MechUnitM(MechUnit source) {
        if(source instanceof UnitModification)throw new IllegalArgumentException("UnitModification based on itself");
        reference=source;
    }

    @Override
    public String toString() {
        return reference.toString()+"/modifiers:"+modifiers;
    }

    @Override
    public Building buildOn() {
        return reference.buildOn();
    }

    @Override
    public Player getPlayer() {
        return reference.getPlayer();
    }

    @Override
    public String getControllerName() {
        return reference.getControllerName();
    }

    @Override
    public BuildPlan buildPlan() {
        return reference.buildPlan();
    }

    @Override
    public Item getMineResult(Tile tile) {
        return reference.getMineResult(tile);
    }

    @Override
    public Tile tileOn() {
        return reference.tileOn();
    }

    @Override
    public Floor drownFloor() {
        return reference.drownFloor();
    }

    @Override
    public CoreBlock.CoreBuild closestCore() {
        return reference.closestCore();
    }

    @Override
    public CoreBlock.CoreBuild closestEnemyCore() {
        return reference.closestEnemyCore();
    }

    @Override
    public CoreBlock.CoreBuild core() {
        return reference.core();
    }

    @Override
    public boolean isRotate() {
        return reference.isRotate();
    }

    @Override
    public float baseRotation() {
        return reference.baseRotation();
    }

    @Override
    public float walkExtension() {
        return reference.walkExtension();
    }

    @Override
    public float walkTime() {
        return reference.walkTime();
    }

    @Override
    public int classId() {
        return reference.classId();
    }

    @Override
    public void baseRotation(float baseRotation) {
        reference.baseRotation(baseRotation);
    }

    @Override
    public void walkExtension(float walkExtension) {
        reference.walkExtension(walkExtension);
    }

    @Override
    public void walkTime(float walkTime) {
        reference.walkTime(walkTime);
    }

    @Override
    public void read(Reads read) {
        reference.read(read);
        modifiers=UnitModification.readModifiers(read);
    }

    @Override
    public void write(Writes write) {
        reference.write(write);
        UnitModification.writeModifiers(write,modifiers);
    }

    @Override
    public <T extends Entityc> T self() {
        return reference.self();
    }

    @Override
    public <T> T as() {
        return reference.as();
    }

    @Override
    public Color statusColor() {
        return reference.statusColor();
    }

    @Override
    public TextureRegion icon() {
        return reference.icon();
    }

    @Override
    public Bits statusBits() {
        return reference.statusBits();
    }

    @Override
    public boolean acceptsItem(Item item) {
        return reference.acceptsItem(item);
    }

    @Override
    public boolean activelyBuilding() {
        return reference.activelyBuilding();
    }

    @Override
    public boolean allowCommand() {
        return reference.allowCommand();
    }

    @Override
    public boolean canBuild() {
        return reference.canBuild();
    }

    @Override
    public boolean canDrown() {
        return reference.canDrown();
    }

    @Override
    public boolean canLand() {
        return reference.canLand();
    }

    @Override
    public boolean canMine() {
        return reference.canMine();
    }

    @Override
    public boolean canMine(Item item) {
        return reference.canMine(item);
    }

    @Override
    public boolean canPass(int tileX, int tileY) {
        return reference.canPass(tileX, tileY);
    }

    @Override
    public boolean canPassOn() {
        return reference.canPassOn();
    }

    @Override
    public boolean canShoot() {
        return reference.canShoot();
    }

    @Override
    public boolean canTarget(Teamc other) {
        return reference.canTarget(other);
    }

    @Override
    public boolean cheating() {
        return reference.cheating();
    }

    @Override
    public boolean checkTarget(boolean targetAir, boolean targetGround) {
        return reference.checkTarget(targetAir, targetGround);
    }

    @Override
    public boolean collides(Hitboxc other) {
        return reference.collides(other);
    }

    @Override
    public boolean damaged() {
        return reference.damaged();
    }

    @Override
    public boolean displayable() {
        return reference.displayable();
    }

    @Override
    public boolean hasEffect(StatusEffect effect) {
        return reference.hasEffect(effect);
    }

    @Override
    public boolean hasItem() {
        return reference.hasItem();
    }

    @Override
    public boolean hasWeapons() {
        return reference.hasWeapons();
    }

    @Override
    public boolean hittable() {
        return reference.hittable();
    }

    @Override
    public boolean ignoreSolids() {
        return reference.ignoreSolids();
    }

    @Override
    public boolean inFogTo(Team viewer) {
        return reference.inFogTo(viewer);
    }

    @Override
    public boolean inRange(Position other) {
        return reference.inRange(other);
    }

    @Override
    public boolean isAI() {
        return reference.isAI();
    }

    @Override
    public boolean isAdded() {
        return reference.isAdded();
    }

    @Override
    public boolean isBoss() {
        return reference.isBoss();
    }

    @Override
    public boolean isBuilding() {
        return reference.isBuilding();
    }

    @Override
    public boolean isCommandable() {
        return reference.isCommandable();
    }

    @Override
    public boolean isEnemy() {
        return reference.isEnemy();
    }

    @Override
    public boolean isFlying() {
        return reference.isFlying();
    }

    @Override
    public boolean isGrounded() {
        return reference.isGrounded();
    }

    @Override
    public boolean isImmune(StatusEffect effect) {
        return reference.isImmune(effect);
    }

    @Override
    public boolean isLocal() {
        return reference.isLocal();
    }

    @Override
    public boolean isMissile() {
        return reference.isMissile();
    }

    @Override
    public boolean isPathImpassable(int tileX, int tileY) {
        return reference.isPathImpassable(tileX, tileY);
    }

    @Override
    public boolean isPlayer() {
        return reference.isPlayer();
    }

    @Override
    public boolean isRemote() {
        return reference.isRemote();
    }

    @Override
    public boolean isSyncHidden(Player player) {
        return reference.isSyncHidden(player);
    }

    @Override
    public boolean isValid() {
        return reference.isValid();
    }

    @Override
    public boolean mining() {
        return reference.mining();
    }

    @Override
    public boolean moving() {
        return reference.moving();
    }

    @Override
    public boolean offloadImmediately() {
        return reference.offloadImmediately();
    }

    @Override
    public boolean onSolid() {
        return reference.onSolid();
    }

    @Override
    public boolean playerControllable() {
        return reference.playerControllable();
    }

    @Override
    public boolean serialize() {
        return reference.serialize();
    }

    @Override
    public boolean shouldSkip(BuildPlan plan, Building core) {
        return reference.shouldSkip(plan, core);
    }

    @Override
    public boolean shouldUpdateController() {
        return reference.shouldUpdateController();
    }

    @Override
    public boolean targetable(Team targeter) {
        return reference.targetable(targeter);
    }

    @Override
    public boolean validMine(Tile tile) {
        return reference.validMine(tile);
    }

    @Override
    public boolean validMine(Tile tile, boolean checkDst) {
        return reference.validMine(tile, checkDst);
    }

    @Override
    public double sense(Content content) {
        return reference.sense(content);
    }

    @Override
    public double sense(LAccess sensor) {
        return reference.sense(sensor);
    }

    @Override
    public float ammof() {
        return reference.ammof();
    }

    @Override
    public float bounds() {
        return reference.bounds();
    }

    @Override
    public float clipSize() {
        return reference.clipSize();
    }

    @Override
    public float deltaAngle() {
        return reference.deltaAngle();
    }

    @Override
    public float deltaLen() {
        return reference.deltaLen();
    }

    @Override
    public float floorSpeedMultiplier() {
        return reference.floorSpeedMultiplier();
    }

    @Override
    public float getDuration(StatusEffect effect) {
        return reference.getDuration(effect);
    }

    @Override
    public float getX() {
        return reference.getX();
    }

    @Override
    public float getY() {
        return reference.getY();
    }

    @Override
    public float healthf() {
        return reference.healthf();
    }

    @Override
    public float hitSize() {
        return reference.hitSize();
    }

    @Override
    public float mass() {
        return reference.mass();
    }

    @Override
    public float physicSize() {
        return reference.physicSize();
    }

    @Override
    public float prefRotation() {
        return reference.prefRotation();
    }

    @Override
    public float range() {
        return reference.range();
    }

    @Override
    public float speed() {
        return reference.speed();
    }

    @Override
    public float walkExtend(boolean scaled) {
        return reference.walkExtend(scaled);
    }

    @Override
    public int cap() {
        return reference.cap();
    }

    @Override
    public int collisionLayer() {
        return reference.collisionLayer();
    }

    @Override
    public int count() {
        return reference.count();
    }

    @Override
    public int itemCapacity() {
        return reference.itemCapacity();
    }

    @Override
    public int maxAccepted(Item item) {
        return reference.maxAccepted(item);
    }

    @Override
    public int tileX() {
        return reference.tileX();
    }

    @Override
    public int tileY() {
        return reference.tileY();
    }

    @Override
    public Object senseObject(LAccess sensor) {
        return reference.senseObject(sensor);
    }

    @Override
    public CommandAI command() {
        return reference.command();
    }

    @Override
    public EntityCollisions.SolidPred solidity() {
        return reference.solidity();
    }

    @Override
    public StatusEntry applyDynamicStatus() {
        return reference.applyDynamicStatus();
    }

    @Override
    public UnitController controller() {
        return reference.controller();
    }

    @Override
    public Item item() {
        return reference.item();
    }

    @Override
    public Block blockOn() {
        return reference.blockOn();
    }

    @Override
    public Floor floorOn() {
        return reference.floorOn();
    }

    @Override
    public void add() {
        reference.add();
    }

    @Override
    public void addBuild(BuildPlan place) {
        reference.addBuild(place);
    }

    @Override
    public void addBuild(BuildPlan place, boolean tail) {
        reference.addBuild(place, tail);
    }

    @Override
    public void addItem(Item item) {
        reference.addItem(item);
    }

    @Override
    public void addItem(Item item, int amount) {
        reference.addItem(item, amount);
    }

    @Override
    public void afterRead() {
        reference.afterRead();
    }

    @Override
    public void afterReadAll() {
        reference.afterReadAll();
    }

    @Override
    public void afterSync() {
        reference.afterSync();
    }

    @Override
    public void aim(Position pos) {
        reference.aim(pos);
    }

    @Override
    public void aim(float x, float y) {
        reference.aim(x, y);
    }

    @Override
    public void aimLook(Position pos) {
        reference.aimLook(pos);
    }

    @Override
    public void aimLook(float x, float y) {
        reference.aimLook(x, y);
    }

    @Override
    public void apply(StatusEffect effect) {
        reference.apply(effect);
    }

    @Override
    public void apply(StatusEffect effect, float duration) {
        reference.apply(effect, duration);
    }

    @Override
    public void approach(Vec2 vector) {
        reference.approach(vector);
    }

    @Override
    public void beforeWrite() {
        reference.beforeWrite();
    }

    @Override
    public void clampHealth() {
        reference.clampHealth();
    }

    @Override
    public void clearBuilding() {
        reference.clearBuilding();
    }

    @Override
    public void clearItem() {
        reference.clearItem();
    }

    @Override
    public void clearStatuses() {
        reference.clearStatuses();
    }

    @Override
    public void collision(Hitboxc other, float x, float y) {
        reference.collision(other, x, y);
    }

    @Override
    public void controlWeapons(boolean rotate, boolean shoot) {
        reference.controlWeapons(rotate, shoot);
    }

    @Override
    public void controlWeapons(boolean rotateShoot) {
        reference.controlWeapons(rotateShoot);
    }

    @Override
    public void controller(UnitController next) {
        reference.controller(next);
    }

    @Override
    public void damage(float amount) {
        reference.damage(amount);
    }

    @Override
    public void damage(float amount, boolean withEffect) {
        reference.damage(amount, withEffect);
    }

    @Override
    public void damageContinuous(float amount) {
        reference.damageContinuous(amount);
    }

    @Override
    public void damageContinuousPierce(float amount) {
        reference.damageContinuousPierce(amount);
    }

    @Override
    public void damagePierce(float amount) {
        reference.damagePierce(amount);
    }

    @Override
    public void damagePierce(float amount, boolean withEffect) {
        reference.damagePierce(amount, withEffect);
    }

    @Override
    public void destroy() {
        reference.destroy();
    }

    @Override
    public void display(Table table) {
        reference.display(table);
    }

    @Override
    public void draw() {
        reference.draw();
    }

    @Override
    public void drawBuildPlans() {
        reference.drawBuildPlans();
    }

    @Override
    public void drawBuilding() {
        reference.drawBuilding();
    }

    @Override
    public void drawBuildingBeam(float px, float py) {
        reference.drawBuildingBeam(px, py);
    }

    @Override
    public void drawPlan(BuildPlan plan, float alpha) {
        reference.drawPlan(plan, alpha);
    }

    @Override
    public void drawPlanTop(BuildPlan plan, float alpha) {
        reference.drawPlanTop(plan, alpha);
    }

    @Override
    public void getCollisions(Cons<QuadTree> consumer) {
        reference.getCollisions(consumer);
    }

    @Override
    public void handleSyncHidden() {
        reference.handleSyncHidden();
    }

    @Override
    public void heal() {
        reference.heal();
    }

    @Override
    public void heal(float amount) {
        reference.heal(amount);
    }

    @Override
    public void healFract(float amount) {
        reference.healFract(amount);
    }

    @Override
    public void hitbox(Rect rect) {
        reference.hitbox(rect);
    }

    @Override
    public void hitboxTile(Rect rect) {
        reference.hitboxTile(rect);
    }

    @Override
    public void impulse(Vec2 v) {
        reference.impulse(v);
    }

    @Override
    public void impulse(float x, float y) {
        reference.impulse(x, y);
    }

    @Override
    public void impulseNet(Vec2 v) {
        reference.impulseNet(v);
    }

    @Override
    public void interpolate() {
        reference.interpolate();
    }

    @Override
    public void kill() {
        reference.kill();
    }

    @Override
    public void killed() {
        reference.killed();
    }

    @Override
    public void landed() {
        reference.landed();
    }

    @Override
    public void lookAt(Position pos) {
        reference.lookAt(pos);
    }

    @Override
    public void lookAt(float angle) {
        reference.lookAt(angle);
    }

    @Override
    public void lookAt(float x, float y) {
        reference.lookAt(x, y);
    }

    @Override
    public void move(Vec2 v) {
        reference.move(v);
    }

    @Override
    public void move(float cx, float cy) {
        reference.move(cx, cy);
    }

    @Override
    public void moveAt(Vec2 vector) {
        reference.moveAt(vector);
    }

    @Override
    public void moveAt(Vec2 vector, float acceleration) {
        reference.moveAt(vector, acceleration);
    }

    @Override
    public void movePref(Vec2 movement) {
        reference.movePref(movement);
    }

    @Override
    public void rawDamage(float amount) {
        reference.rawDamage(amount);
    }

    @Override
    public void readSync(Reads read) {
        reference.readSync(read);
    }

    @Override
    public void readSyncManual(FloatBuffer buffer) {
        reference.readSyncManual(buffer);
    }

    @Override
    public void remove() {
        reference.remove();
    }

    @Override
    public void removeBuild(int x, int y, boolean breaking) {
        reference.removeBuild(x, y, breaking);
    }

    @Override
    public void resetController() {
        reference.resetController();
    }

    @Override
    public void rotateMove(Vec2 vec) {
        reference.rotateMove(vec);
    }

    @Override
    public void set(Position pos) {
        reference.set(pos);
    }

    @Override
    public void set(float x, float y) {
        reference.set(x, y);
    }

    @Override
    public void set(UnitType def, UnitController controller) {
        reference.set(def, controller);
    }

    @Override
    public void setProp(UnlockableContent content, double value) {
        reference.setProp(content, value);
    }

    @Override
    public void setProp(LAccess prop, double value) {
        reference.setProp(prop, value);
    }

    @Override
    public void setProp(LAccess prop, Object value) {
        reference.setProp(prop, value);
    }

    @Override
    public void setType(UnitType type) {
        reference.setType(type);
    }

    @Override
    public void setWeaponRotation(float rotation) {
        reference.setWeaponRotation(rotation);
    }

    @Override
    public void setupWeapons(UnitType def) {
        reference.setupWeapons(def);
    }

    @Override
    public void snapInterpolation() {
        reference.snapInterpolation();
    }

    @Override
    public void snapSync() {
        reference.snapSync();
    }

    @Override
    public void statusArmor(float armor) {
        reference.statusArmor(armor);
    }

    @Override
    public void statusBuildSpeed(float buildSpeed) {
        reference.statusBuildSpeed(buildSpeed);
    }

    @Override
    public void statusDamageMultiplier(float damageMultiplier) {
        reference.statusDamageMultiplier(damageMultiplier);
    }

    @Override
    public void statusDrag(float drag) {
        reference.statusDrag(drag);
    }

    @Override
    public void statusMaxHealth(float health) {
        reference.statusMaxHealth(health);
    }

    @Override
    public void statusReloadMultiplier(float reloadMultiplier) {
        reference.statusReloadMultiplier(reloadMultiplier);
    }

    @Override
    public void statusSpeed(float speed) {
        reference.statusSpeed(speed);
    }

    @Override
    public void trns(Position pos) {
        reference.trns(pos);
    }

    @Override
    public void trns(float x, float y) {
        reference.trns(x, y);
    }

    @Override
    public void unapply(StatusEffect effect) {
        reference.unapply(effect);
    }

    @Override
    public void unloaded() {
        reference.unloaded();
    }

    @Override
    public void update() {
        reference.update();
    }

    @Override
    public void updateBoosting(boolean boost) {
        reference.updateBoosting(boost);
    }

    @Override
    public void updateBuildLogic() {
        reference.updateBuildLogic();
    }

    @Override
    public void updateDrowning() {
        reference.updateDrowning();
    }

    @Override
    public void updateLastPosition() {
        reference.updateLastPosition();
    }

    @Override
    public void validatePlans() {
        reference.validatePlans();
    }

    @Override
    public void velAddNet(Vec2 v) {
        reference.velAddNet(v);
    }

    @Override
    public void velAddNet(float vx, float vy) {
        reference.velAddNet(vx, vy);
    }

    @Override
    public void wobble() {
        reference.wobble();
    }

    @Override
    public void writeSync(Writes write) {
        reference.writeSync(write);
    }

    @Override
    public void writeSyncManual(FloatBuffer buffer) {
        reference.writeSyncManual(buffer);
    }

    @Override
    public Vec2 vel() {
        return reference.vel();
    }

    @Override
    public Queue<BuildPlan> plans() {
        return reference.plans();
    }

    @Override
    public boolean dead() {
        return reference.dead();
    }

    @Override
    public boolean disarmed() {
        return reference.disarmed();
    }

    @Override
    public boolean isShooting() {
        return reference.isShooting();
    }

    @Override
    public boolean spawnedByCore() {
        return reference.spawnedByCore();
    }

    @Override
    public boolean updateBuilding() {
        return reference.updateBuilding();
    }

    @Override
    public double flag() {
        return reference.flag();
    }

    @Override
    public float aimX() {
        return reference.aimX();
    }

    @Override
    public float aimY() {
        return reference.aimY();
    }

    @Override
    public float ammo() {
        return reference.ammo();
    }

    @Override
    public float armor() {
        return reference.armor();
    }

    @Override
    public float armorOverride() {
        return reference.armorOverride();
    }

    @Override
    public float buildAlpha() {
        return reference.buildAlpha();
    }

    @Override
    public float buildSpeedMultiplier() {
        return reference.buildSpeedMultiplier();
    }

    @Override
    public float damageMultiplier() {
        return reference.damageMultiplier();
    }

    @Override
    public float deltaX() {
        return reference.deltaX();
    }

    @Override
    public float deltaY() {
        return reference.deltaY();
    }

    @Override
    public float drag() {
        return reference.drag();
    }

    @Override
    public float dragMultiplier() {
        return reference.dragMultiplier();
    }

    @Override
    public float drownTime() {
        return reference.drownTime();
    }

    @Override
    public float elevation() {
        return reference.elevation();
    }

    @Override
    public float healTime() {
        return reference.healTime();
    }

    @Override
    public float health() {
        return reference.health();
    }

    @Override
    public float healthMultiplier() {
        return reference.healthMultiplier();
    }

    @Override
    public float hitTime() {
        return reference.hitTime();
    }

    @Override
    public float itemTime() {
        return reference.itemTime();
    }

    @Override
    public float lastX() {
        return reference.lastX();
    }

    @Override
    public float lastY() {
        return reference.lastY();
    }

    @Override
    public float maxHealth() {
        return reference.maxHealth();
    }

    @Override
    public float mineTimer() {
        return reference.mineTimer();
    }

    @Override
    public float reloadMultiplier() {
        return reference.reloadMultiplier();
    }

    @Override
    public float rotation() {
        return reference.rotation();
    }

    @Override
    public float shadowAlpha() {
        return reference.shadowAlpha();
    }

    @Override
    public float shield() {
        return reference.shield();
    }

    @Override
    public float shieldAlpha() {
        return reference.shieldAlpha();
    }

    @Override
    public float speedMultiplier() {
        return reference.speedMultiplier();
    }

    @Override
    public float splashTimer() {
        return reference.splashTimer();
    }

    @Override
    public float x() {
        return reference.x();
    }

    @Override
    public float y() {
        return reference.y();
    }

    @Override
    public int id() {
        return reference.id();
    }

    @Override
    public int lastFogPos() {
        return reference.lastFogPos();
    }

    @Override
    public String lastCommanded() {
        return reference.lastCommanded();
    }

    @Override
    public long lastUpdated() {
        return reference.lastUpdated();
    }

    @Override
    public long updateSpacing() {
        return reference.updateSpacing();
    }

    @Override
    public PhysicsProcess.PhysicRef physref() {
        return reference.physref();
    }

    @Override
    public Ability[] abilities() {
        return reference.abilities();
    }

    @Override
    public WeaponMount[] mounts() {
        return reference.mounts();
    }

    @Override
    public Team team() {
        return reference.team();
    }

    @Override
    public Trail trail() {
        return reference.trail();
    }

    @Override
    public ItemStack stack() {
        return reference.stack();
    }

    @Override
    public UnitType dockedType() {
        return reference.dockedType();
    }

    @Override
    public UnitType type() {
        return reference.type();
    }

    @Override
    public Tile mineTile() {
        return reference.mineTile();
    }

    @Override
    public Floor lastDrownFloor() {
        return reference.lastDrownFloor();
    }

    @Override
    public void abilities(Ability[] abilities) {
        reference.abilities(abilities);
    }

    @Override
    public void aimX(float aimX) {
        reference.aimX(aimX);
    }

    @Override
    public void aimY(float aimY) {
        reference.aimY(aimY);
    }

    @Override
    public void ammo(float ammo) {
        reference.ammo(ammo);
    }

    @Override
    public void armor(float armor) {
        reference.armor(armor);
    }

    @Override
    public void armorOverride(float armorOverride) {
        reference.armorOverride(armorOverride);
    }

    @Override
    public void buildAlpha(float buildAlpha) {
        reference.buildAlpha(buildAlpha);
    }

    @Override
    public void buildSpeedMultiplier(float buildSpeedMultiplier) {
        reference.buildSpeedMultiplier(buildSpeedMultiplier);
    }

    @Override
    public void damageMultiplier(float damageMultiplier) {
        reference.damageMultiplier(damageMultiplier);
    }

    @Override
    public void dead(boolean dead) {
        reference.dead(dead);
    }

    @Override
    public void deltaX(float deltaX) {
        reference.deltaX(deltaX);
    }

    @Override
    public void deltaY(float deltaY) {
        reference.deltaY(deltaY);
    }

    @Override
    public void disarmed(boolean disarmed) {
        reference.disarmed(disarmed);
    }

    @Override
    public void dockedType(UnitType dockedType) {
        reference.dockedType(dockedType);
    }

    @Override
    public void drag(float drag) {
        reference.drag(drag);
    }

    @Override
    public void dragMultiplier(float dragMultiplier) {
        reference.dragMultiplier(dragMultiplier);
    }

    @Override
    public void drownTime(float drownTime) {
        reference.drownTime(drownTime);
    }

    @Override
    public void elevation(float elevation) {
        reference.elevation(elevation);
    }

    @Override
    public void flag(double flag) {
        reference.flag(flag);
    }

    @Override
    public void healTime(float healTime) {
        reference.healTime(healTime);
    }

    @Override
    public void health(float health) {
        reference.health(health);
    }

    @Override
    public void healthMultiplier(float healthMultiplier) {
        reference.healthMultiplier(healthMultiplier);
    }

    @Override
    public void hitSize(float hitSize) {
        reference.hitSize(hitSize);
    }

    @Override
    public void hitTime(float hitTime) {
        reference.hitTime(hitTime);
    }

    @Override
    public void id(int id) {
        reference.id(id);
    }

    @Override
    public void isShooting(boolean isShooting) {
        reference.isShooting(isShooting);
    }

    @Override
    public void itemTime(float itemTime) {
        reference.itemTime(itemTime);
    }

    @Override
    public void lastCommanded(String lastCommanded) {
        reference.lastCommanded(lastCommanded);
    }

    @Override
    public void lastDrownFloor(Floor lastDrownFloor) {
        reference.lastDrownFloor(lastDrownFloor);
    }

    @Override
    public void lastFogPos(int lastFogPos) {
        reference.lastFogPos(lastFogPos);
    }

    @Override
    public void lastUpdated(long lastUpdated) {
        reference.lastUpdated(lastUpdated);
    }

    @Override
    public void lastX(float lastX) {
        reference.lastX(lastX);
    }

    @Override
    public void lastY(float lastY) {
        reference.lastY(lastY);
    }

    @Override
    public void maxHealth(float maxHealth) {
        reference.maxHealth(maxHealth);
    }

    @Override
    public void mineTile(Tile mineTile) {
        reference.mineTile(mineTile);
    }

    @Override
    public void mineTimer(float mineTimer) {
        reference.mineTimer(mineTimer);
    }

    @Override
    public void mounts(WeaponMount[] mounts) {
        reference.mounts(mounts);
    }

    @Override
    public void physref(PhysicsProcess.PhysicRef physref) {
        reference.physref(physref);
    }

    @Override
    public void plans(Queue<BuildPlan> plans) {
        reference.plans(plans);
    }

    @Override
    public void reloadMultiplier(float reloadMultiplier) {
        reference.reloadMultiplier(reloadMultiplier);
    }

    @Override
    public void rotation(float rotation) {
        reference.rotation(rotation);
    }

    @Override
    public void setIndex__all(int index) {
        reference.setIndex__all(index);
    }

    @Override
    public void setIndex__draw(int index) {
        reference.setIndex__draw(index);
    }

    @Override
    public void setIndex__sync(int index) {
        reference.setIndex__sync(index);
    }

    @Override
    public void setIndex__unit(int index) {
        reference.setIndex__unit(index);
    }

    @Override
    public void shadowAlpha(float shadowAlpha) {
        reference.shadowAlpha(shadowAlpha);
    }

    @Override
    public void shield(float shield) {
        reference.shield(shield);
    }

    @Override
    public void shieldAlpha(float shieldAlpha) {
        reference.shieldAlpha(shieldAlpha);
    }

    @Override
    public void spawnedByCore(boolean spawnedByCore) {
        reference.spawnedByCore(spawnedByCore);
    }

    @Override
    public void speedMultiplier(float speedMultiplier) {
        reference.speedMultiplier(speedMultiplier);
    }

    @Override
    public void splashTimer(float splashTimer) {
        reference.splashTimer(splashTimer);
    }

    @Override
    public void stack(ItemStack stack) {
        reference.stack(stack);
    }

    @Override
    public void team(Team team) {
        reference.team(team);
    }

    @Override
    public void trail(Trail trail) {
        reference.trail(trail);
    }

    @Override
    public void type(UnitType type) {
        reference.type(type);
    }

    @Override
    public void updateBuilding(boolean updateBuilding) {
        reference.updateBuilding(updateBuilding);
    }

    @Override
    public void updateSpacing(long updateSpacing) {
        reference.updateSpacing(updateSpacing);
    }

    @Override
    public void vel(Vec2 vel) {
        reference.vel(vel);
    }

    @Override
    public void x(float x) {
        reference.x(x);
    }

    @Override
    public void y(float y) {
        reference.y(y);
    }

    @Override
    public float angleTo(Position other) {
        return reference.angleTo(other);
    }

    @Override
    public float angleTo(float x, float y) {
        return reference.angleTo(x, y);
    }

    @Override
    public float dst2(Position other) {
        return reference.dst2(other);
    }

    @Override
    public float dst(Position other) {
        return reference.dst(other);
    }

    @Override
    public float dst(float x, float y) {
        return reference.dst(x, y);
    }

    @Override
    public float dst2(float x, float y) {
        return reference.dst2(x, y);
    }

    @Override
    public boolean within(Position other, float dst) {
        return reference.within(other, dst);
    }

    @Override
    public boolean within(float x, float y, float dst) {
        return reference.within(x, y, dst);
    }
}
