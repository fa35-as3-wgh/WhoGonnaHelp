package fa35.group2.view.tui.prints;

import fa35.group2.model.entities.SkillEntity;

import java.util.List;

public class SkillSubmenuPrints
{
    private PrintHelper printHelper;

    public SkillSubmenuPrints()
    {
        this.printHelper = new PrintHelper();
    }

    public void printSkillSubmenuAdd()
    {
        System.out.println();
        System.out.println("Fertigkeit Hinzufügen");
        System.out.println("-------------");
        this.printHelper.printEmptyImputForAbortion();
        this.printHelper.printGiveName();
    }

    public void printSkillSubmenuDelete(List<SkillEntity> skillEntities)
    {
        System.out.println();
        System.out.println("Fertigkeit löschen");
        this.printHelper.printSkillsWithIdAndName(skillEntities);
        this.printHelper.printEmptyImputForAbortion();
        this.printHelper.printGiveId();
    }

    public void printSkillSubmenuAll(List<SkillEntity> skillEntities)
    {
        this.printHelper.printSkill();
        this.printHelper.printSkillsWithIdAndName(skillEntities);
    }
}
