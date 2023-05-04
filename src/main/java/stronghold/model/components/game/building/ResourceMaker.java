package stronghold.model.components.game.building;


import stronghold.model.components.game.Government;
import stronghold.model.components.game.enums.Resource;
import stronghold.model.components.game.enums.Texture;
import stronghold.model.components.general.User;

public class ResourceMaker extends Building {
    private ResourceMakerType resourceMakerType;
    private Resource resource;
    private int rate;

    public ResourceMaker(Government ownership, ResourceMakerType resourceMakerType) {
        super(ownership, resourceMakerType.getHealth(), resourceMakerType.getGold(), resourceMakerType.getWorkerNum(), resourceMakerType.isEngineerWorkers(), resourceMakerType.getNeededResource(), resourceMakerType.getNeededResourceCount());
        this.resourceMakerType = resourceMakerType;
        this.resource = resourceMakerType.getResource();
        this.rate = resourceMakerType.getRate();
    }

    public Resource getResource() {
        return resource;
    }

    public int getRate() {
        return rate;
    }


    public boolean checkTexture(Texture texture) {
        if (resourceMakerType.equals(ResourceMakerType.IRON_MINE)
                && !texture.equals(Texture.STONE)) {
            return false;
        } else if (resourceMakerType.equals(ResourceMakerType.PITCH_RIG)
                && !texture.equals(Texture.PLAIN)) {
            return false;
        } else if (texture.getColor().equals("BLUE")
                && texture.equals(Texture.ROCK)) {
            return false;
        }
        return true;
    }
}

