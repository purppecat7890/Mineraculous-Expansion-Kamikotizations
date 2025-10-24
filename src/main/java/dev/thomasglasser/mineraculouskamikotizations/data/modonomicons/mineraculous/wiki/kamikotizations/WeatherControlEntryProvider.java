package dev.thomasglasser.mineraculouskamikotizations.data.modonomicons.mineraculous.wiki.kamikotizations;

import com.klikli_dev.modonomicon.api.datagen.CategoryProviderBase;
import com.klikli_dev.modonomicon.api.datagen.IndexModeEntryProvider;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookImagePageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import dev.thomasglasser.mineraculouskamikotizations.data.modonomicons.mineraculous.wiki.AddToWikiBookSubProvider;
import dev.thomasglasser.mineraculouskamikotizations.world.item.MineraculousKamikotizationsItems;

public class WeatherControlEntryProvider extends IndexModeEntryProvider {
    public static final String ID = "weather_control";

    public WeatherControlEntryProvider(CategoryProviderBase parent) {
        super(parent);
    }

    @Override
    protected void generatePages() {
        page("receiving", () -> BookImagePageModel.create()
                .withImages(AddToWikiBookSubProvider.wikiTexture("kamikotizations/weather_control/receiving.png"))
                .withTitle(context().pageTitle())
                .withText(context().pageText()));

        add(context().pageTitle(), "Receiving");
        add(context().pageText(), """
                The Weather Control kamikotization can be applied to any umbrella, including those from other mods (as long as they are in the 'c:tools/umbrella' tag).
                It has the default name "Stormy Weather".
                """);

        page("tool", () -> BookTextPageModel.create()
                .withTitle(context().pageTitle())
                .withText(context().pageText()));

        add(context().pageTitle(), "Tool");
        add(context().pageText(), """
                The tool of the Weather Control kamikotization is a Weather Control Parasol.
                It has 4 abilities that can be set via the tool wheel (default: H) and activated by right clicking:
                - Ice
                - Lightning
                - Weather
                - Wind\\
                When in any equipment slot, the holder is able to fly.
                """);

        page("ice", () -> BookTextPageModel.create()
                .withTitle(context().pageTitle())
                .withText(context().pageText()));

        add(context().pageTitle(), "Ice");
        add(context().pageText(), """
                The Ice mode will fire Ice Charges that freeze entities and blocks that they hit.
                When frozen, entities will be captured in a prison of ice and will freeze to death.
                """);

        page("lightning", () -> BookTextPageModel.create()
                .withTitle(context().pageTitle())
                .withText(context().pageText()));

        add(context().pageTitle(), "Lightning");
        add(context().pageText(), """
                The Lightning mode will fire Lightning Bolts that will damage entities and blocks that they hit, lighting fires in the process.
                """);

        page("weather", () -> BookTextPageModel.create()
                .withTitle(context().pageTitle())
                .withText(context().pageText()));

        add(context().pageTitle(), "Weather");
        add(context().pageText(), """
                The Weather mode will cycle the weather to rain, thunder, or clear.
                """);

        page("wind", () -> BookTextPageModel.create()
                .withTitle(context().pageTitle())
                .withText(context().pageText()));

        add(context().pageTitle(), "Wind");
        add(context().pageText(), """
                The Wind mode will fire Wind Charges that will push entities and blocks that they hit.
                """);
    }

    @Override
    protected String entryName() {
        return "Weather Control";
    }

    @Override
    protected String entryDescription() {
        return "Powers to control the weather.";
    }

    @Override
    protected BookIconModel entryIcon() {
        return BookIconModel.create(MineraculousKamikotizationsItems.WEATHER_CONTROL_PARASOL);
    }

    @Override
    protected String entryId() {
        return ID;
    }
}
