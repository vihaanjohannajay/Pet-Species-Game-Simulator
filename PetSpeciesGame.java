import java.util.*;

// ===================== PetSpecies =====================
final class PetSpecies
{
    private final String speciesName;
    private final String[] evolutionStages;
    private final int maxLifespan;
    private final String habitat;

    public PetSpecies(String speciesName, String[] evolutionStages, int maxLifespan, String habitat)
    {
        if (speciesName == null || speciesName.isBlank())
            throw new IllegalArgumentException("Species name cannot be empty");
        if (evolutionStages == null || evolutionStages.length == 0)
            throw new IllegalArgumentException("Must have at least one evolution stage");
        if (maxLifespan <= 0)
            throw new IllegalArgumentException("Max lifespan must be positive");
        if (habitat == null || habitat.isBlank())
            throw new IllegalArgumentException("Habitat cannot be empty");

        this.speciesName = speciesName;
        this.evolutionStages = Arrays.copyOf(evolutionStages, evolutionStages.length);
        this.maxLifespan = maxLifespan;
        this.habitat = habitat;
    }

    public String getSpeciesName() { return speciesName; }
    public String[] getEvolutionStages() { return Arrays.copyOf(evolutionStages, evolutionStages.length); }
    public int getMaxLifespan() { return maxLifespan; }
    public String getHabitat() { return habitat; }

    @Override
    public String toString()
    {
        return speciesName + " (Habitat: " + habitat + ", Lifespan: " + maxLifespan + ")";
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof PetSpecies)) return false;
        PetSpecies that = (PetSpecies) o;
        return maxLifespan == that.maxLifespan &&
               speciesName.equals(that.speciesName) &&
               Arrays.equals(evolutionStages, that.evolutionStages) &&
               habitat.equals(that.habitat);
    }

    @Override
    public int hashCode()
    {
        int result = Objects.hash(speciesName, maxLifespan, habitat);
        result = 31 * result + Arrays.hashCode(evolutionStages);
        return result;
    }
}

// ===================== VirtualPet =====================
class VirtualPet
{
    private final String petId;
    private final PetSpecies species;
    private final long birthTimestamp;

    private String petName;
    private int age;
    private int happiness;
    private int health;
    private int currentStageIndex = 0;

    protected static final String[] DEFAULT_EVOLUTION_STAGES = {"Egg", "Child", "Adult"};
    static final int MAX_HAPPINESS = 100;
    static final int MAX_HEALTH = 100;
    public static final String PET_SYSTEM_VERSION = "2.0";

    public VirtualPet()
    {
        this("Defaulty", new PetSpecies("Generic", DEFAULT_EVOLUTION_STAGES, 100, "Home"), 0, 50, 50);
    }

    public VirtualPet(String name)
    {
        this(name, new PetSpecies("Generic", DEFAULT_EVOLUTION_STAGES, 100, "Home"), 0, 50, 50);
    }

    public VirtualPet(String name, PetSpecies species)
    {
        this(name, species, 0, 50, 50);
    }

    public VirtualPet(String name, PetSpecies species, int age, int happiness, int health)
    {
        this.petId = UUID.randomUUID().toString();
        this.species = Objects.requireNonNull(species);
        this.birthTimestamp = System.currentTimeMillis();
        setPetName(name);
        setAge(age);
        setHappiness(happiness);
        setHealth(health);
    }

    public String getPetId() { return petId; }
    public PetSpecies getSpecies() { return species; }
    public long getBirthTimestamp() { return birthTimestamp; }

    public String getPetName() { return petName; }
    public void setPetName(String petName) { this.petName = petName; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = Math.max(0, age); }

    public int getHappiness() { return happiness; }
    public void setHappiness(int happiness) { this.happiness = validateStat(happiness, MAX_HAPPINESS); }

    public int getHealth() { return health; }
    public void setHealth(int health) { this.health = validateStat(health, MAX_HEALTH); }

    public void feedPet(String foodType)
    {
        modifyHealth(calculateFoodBonus(foodType));
    }

    public void playWithPet(String gameType)
    {
        modifyHappiness(calculateGameEffect(gameType));
    }

    protected int calculateFoodBonus(String foodType)
    {
        return "Meat".equalsIgnoreCase(foodType) ? 10 : 5;
    }

    protected int calculateGameEffect(String gameType)
    {
        return "Fetch".equalsIgnoreCase(gameType) ? 15 : 8;
    }

    // === Private helpers ===
    private int validateStat(int value, int max)
    {
        if (value < 0) return 0;
        if (value > max) return max;
        return value;
    }

    private void modifyHappiness(int delta)
    {
        setHappiness(happiness + delta);
        checkEvolution();
    }

    private void modifyHealth(int delta)
    {
        setHealth(health + delta);
        checkEvolution();
    }

    private void checkEvolution()
    {
        // Simple rule: evolve when both happiness and health > 75
        String[] stages = species.getEvolutionStages();
        if (currentStageIndex < stages.length - 1 && happiness > 75 && health > 75)
        {
            currentStageIndex++;
            System.out.println(petName + " has evolved into " + stages[currentStageIndex] + "!");
        }
    }

    String getInternalState()
    {
        return "[DEBUG] " + petName + " (H:" + health + ", Happy:" + happiness + ")";
    }

    @Override
    public String toString()
    {
        String[] stages = species.getEvolutionStages();
        return "VirtualPet{" +
                "id='" + petId + '\'' +
                ", name='" + petName + '\'' +
                ", stage='" + stages[currentStageIndex] + '\'' +
                ", species=" + species +
                ", age=" + age +
                ", happiness=" + happiness +
                ", health=" + health +
                '}';
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof VirtualPet)) return false;
        VirtualPet that = (VirtualPet) o;
        return petId.equals(that.petId);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(petId);
    }
}

// ===================== DragonPet =====================
class DragonPet
{
    private final String dragonType;
    private final String breathWeapon;
    private final VirtualPet corePet;

    public DragonPet(String name, String dragonType, String breathWeapon)
    {
        this.dragonType = dragonType;
        this.breathWeapon = breathWeapon;
        this.corePet = new VirtualPet(name, new PetSpecies("Dragon",
                new String[]{"Wyrmling", "Young Dragon", "Adult Dragon"}, 500, "Caves"));
    }

    public VirtualPet getCorePet() { return corePet; }
    public String getDragonType() { return dragonType; }
    public String getBreathWeapon() { return breathWeapon; }

    @Override
    public String toString()
    {
        return "DragonPet{" + "type=" + dragonType +
               ", breathWeapon=" + breathWeapon +
               ", core=" + corePet + "}";
    }
}

// ===================== RobotPet =====================
class RobotPet
{
    private boolean needsCharging;
    private int batteryLevel;
    private final VirtualPet corePet;

    public RobotPet(String name)
    {
        this.needsCharging = false;
        this.batteryLevel = 100;
        this.corePet = new VirtualPet(name, new PetSpecies("Robot",
                new String[]{"Prototype", "Advanced Model", "AI Overlord"}, 9999, "Lab"));
    }

    public void drainBattery(int amount)
    {
        batteryLevel = Math.max(0, batteryLevel - amount);
        if (batteryLevel == 0) needsCharging = true;
    }

    public void chargeBattery()
    {
        batteryLevel = 100;
        needsCharging = false;
    }

    public VirtualPet getCorePet() { return corePet; }
    public boolean isNeedsCharging() { return needsCharging; }
    public int getBatteryLevel() { return batteryLevel; }

    @Override
    public String toString()
    {
        return "RobotPet{" + "battery=" + batteryLevel +
               "%, needsCharging=" + needsCharging +
               ", core=" + corePet + "}";
    }
}

// ===================== Demo Main =====================
public class PetSpeciesGame
{
    public static void main(String[] args)
    {
        VirtualPet pet = new VirtualPet("Buddy");
        pet.feedPet("Meat");
        pet.playWithPet("Fetch");
        pet.feedPet("Meat"); // trigger evolution
        pet.playWithPet("Fetch");

        DragonPet dragon = new DragonPet("Smoky", "Fire", "Flame Breath");
        RobotPet robo = new RobotPet("RoboCat");

        System.out.println(pet);
        System.out.println(dragon);
        System.out.println(robo);
    }
}
