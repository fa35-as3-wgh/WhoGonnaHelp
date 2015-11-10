package fa35.group2.view.tui.prints;

import fa35.group2.model.entities.SkillEntity;

import java.util.List;

public class FindFriendBySkillPrints
{
    private PrintHelper printHelper;

    public FindFriendBySkillPrints()
    {
        this.printHelper = new PrintHelper();
    }

    public void printFindFriendBySkill(List<SkillEntity> skillEntities)
    {
        System.out.println("Alle verfügbaren Fertigkeiten");
        this.printHelper.printSkillsWithIdAndName(skillEntities);
        this.printHelper.printEmptyImputForAbortion();
        this.printHelper.printGiveId();
    }
}
