package com.example.model

data class LevelInfo(
    val levelNumber: Int,
    val title: String,
    val subtitle: String,
    val category: String,
    val difficulty: String,
    val questionCount: Int,
    val passingPercentage: Int = 60,
    val xpReward: Int,
    val era: String,
    val iconEmoji: String
)

object LevelCatalog {
    val levels: List<LevelInfo> = listOf(
        LevelInfo(
            levelNumber = 1,
            title = "The Dawn of Civilizations",
            subtitle = "Stone Age roots & earliest human settlements",
            category = HistoryCategories.ANCIENT_CIVILIZATIONS,
            difficulty = "Easy",
            questionCount = 5,
            passingPercentage = 60,
            xpReward = 150,
            era = "Prehistoric - 3000 BCE",
            iconEmoji = "🏛️"
        ),
        LevelInfo(
            levelNumber = 2,
            title = "Cradle of the Indus Valley",
            subtitle = "Mohenjo-daro, Harappa & advanced town planning",
            category = HistoryCategories.ANCIENT_CIVILIZATIONS,
            difficulty = "Easy",
            questionCount = 5,
            passingPercentage = 60,
            xpReward = 200,
            era = "2600 - 1900 BCE",
            iconEmoji = "🏺"
        ),
        LevelInfo(
            levelNumber = 3,
            title = "Mesopotamia & The Nile",
            subtitle = "Cuneiform tablets, Pharaohs & Giza Pyramids",
            category = HistoryCategories.ANCIENT_CIVILIZATIONS,
            difficulty = "Easy",
            questionCount = 5,
            passingPercentage = 60,
            xpReward = 250,
            era = "3100 - 539 BCE",
            iconEmoji = "📐"
        ),
        LevelInfo(
            levelNumber = 4,
            title = "Greek Thinkers & City-States",
            subtitle = "Athens democracy, Sparta & Socrates",
            category = HistoryCategories.WARS_AND_BATTLES,
            difficulty = "Medium",
            questionCount = 6,
            passingPercentage = 65,
            xpReward = 300,
            era = "800 - 146 BCE",
            iconEmoji = "🏺"
        ),
        LevelInfo(
            levelNumber = 5,
            title = "The Roman Empire & Legions",
            subtitle = "Julius Caesar, Colosseum & Pax Romana",
            category = HistoryCategories.ANCIENT_CIVILIZATIONS,
            difficulty = "Medium",
            questionCount = 6,
            passingPercentage = 65,
            xpReward = 350,
            era = "27 BCE - 476 CE",
            iconEmoji = "⚔️"
        ),
        LevelInfo(
            levelNumber = 6,
            title = "Islamic Golden Age of Science",
            subtitle = "House of Wisdom, Al-Khwarizmi & medicine",
            category = HistoryCategories.SCIENCE_INVENTIONS,
            difficulty = "Medium",
            questionCount = 6,
            passingPercentage = 65,
            xpReward = 400,
            era = "8th - 14th Century",
            iconEmoji = "🔭"
        ),
        LevelInfo(
            levelNumber = 7,
            title = "The Silk Road & Caravans",
            subtitle = "Ancient trade routes, spices & paper spread",
            category = HistoryCategories.ANCIENT_CIVILIZATIONS,
            difficulty = "Medium",
            questionCount = 6,
            passingPercentage = 65,
            xpReward = 450,
            era = "130 BCE - 1453 CE",
            iconEmoji = "🐪"
        ),
        LevelInfo(
            levelNumber = 8,
            title = "The Delhi Sultanate Era",
            subtitle = "Qutb-ud-din Aibak, Razia Sultana & Khalji",
            category = HistoryCategories.PAKISTAN_MOVEMENT,
            difficulty = "Medium",
            questionCount = 7,
            passingPercentage = 70,
            xpReward = 500,
            era = "1206 - 1526 CE",
            iconEmoji = "🕌"
        ),
        LevelInfo(
            levelNumber = 9,
            title = "Rise of the Mughal Empire",
            subtitle = "Battle of Panipat, Babur & Emperor Akbar",
            category = HistoryCategories.MUGHAL_DYNASTIES,
            difficulty = "Medium",
            questionCount = 7,
            passingPercentage = 70,
            xpReward = 550,
            era = "1526 - 1605 CE",
            iconEmoji = "👑"
        ),
        LevelInfo(
            levelNumber = 10,
            title = "Mughal Architectural Splendor",
            subtitle = "Shah Jahan, Badshahi Mosque & Taj Mahal",
            category = HistoryCategories.MUGHAL_DYNASTIES,
            difficulty = "Medium",
            questionCount = 7,
            passingPercentage = 70,
            xpReward = 600,
            era = "1628 - 1707 CE",
            iconEmoji = "🏰"
        ),
        LevelInfo(
            levelNumber = 11,
            title = "The Renaissance & Printing Press",
            subtitle = "Gutenberg, Leonardo da Vinci & discoveries",
            category = HistoryCategories.SCIENCE_INVENTIONS,
            difficulty = "Medium",
            questionCount = 8,
            passingPercentage = 70,
            xpReward = 650,
            era = "14th - 17th Century",
            iconEmoji = "📜"
        ),
        LevelInfo(
            levelNumber = 12,
            title = "1857 War of Independence",
            subtitle = "End of East India Company & Sir Syed Ahmad",
            category = HistoryCategories.PAKISTAN_MOVEMENT,
            difficulty = "Hard",
            questionCount = 8,
            passingPercentage = 75,
            xpReward = 700,
            era = "1857 - 1885 CE",
            iconEmoji = "🛡️"
        ),
        LevelInfo(
            levelNumber = 13,
            title = "All-India Muslim League Foundation",
            subtitle = "1906 Dhaka convention & political rights",
            category = HistoryCategories.PAKISTAN_MOVEMENT,
            difficulty = "Hard",
            questionCount = 8,
            passingPercentage = 75,
            xpReward = 750,
            era = "1906 - 1920 CE",
            iconEmoji = "🇵🇰"
        ),
        LevelInfo(
            levelNumber = 14,
            title = "World War I & Khilafat Movement",
            subtitle = "1914–1918 global front & Maulana Muhammad Ali",
            category = HistoryCategories.WARS_AND_BATTLES,
            difficulty = "Hard",
            questionCount = 8,
            passingPercentage = 75,
            xpReward = 800,
            era = "1914 - 1924 CE",
            iconEmoji = "⚔️"
        ),
        LevelInfo(
            levelNumber = 15,
            title = "Allama Iqbal's Historic Address",
            subtitle = "1930 Allahabad vision of a separate homeland",
            category = HistoryCategories.LEADERS,
            difficulty = "Hard",
            questionCount = 8,
            passingPercentage = 75,
            xpReward = 850,
            era = "1930 - 1938 CE",
            iconEmoji = "💡"
        ),
        LevelInfo(
            levelNumber = 16,
            title = "The 1940 Lahore Resolution",
            subtitle = "Minto Park landmark declaration & Quaid-e-Azam",
            category = HistoryCategories.PAKISTAN_MOVEMENT,
            difficulty = "Hard",
            questionCount = 9,
            passingPercentage = 75,
            xpReward = 900,
            era = "23 March 1940",
            iconEmoji = "⭐"
        ),
        LevelInfo(
            levelNumber = 17,
            title = "World War II & Global Power Shift",
            subtitle = "1939–1945 campaigns & birth of the UN",
            category = HistoryCategories.WARS_AND_BATTLES,
            difficulty = "Hard",
            questionCount = 9,
            passingPercentage = 75,
            xpReward = 950,
            era = "1939 - 1945 CE",
            iconEmoji = "🌍"
        ),
        LevelInfo(
            levelNumber = 18,
            title = "14 August 1947: Birth of Pakistan",
            subtitle = "Independence, Liaquat Ali Khan & First Assembly",
            category = HistoryCategories.PAKISTAN_MOVEMENT,
            difficulty = "Hard",
            questionCount = 10,
            passingPercentage = 80,
            xpReward = 1000,
            era = "August 1947",
            iconEmoji = "🇵🇰"
        ),
        LevelInfo(
            levelNumber = 19,
            title = "UNESCO Heritage of Pakistan",
            subtitle = "Taxila Buddhist site, Rohtas & Shalimar Gardens",
            category = HistoryCategories.ANCIENT_CIVILIZATIONS,
            difficulty = "Hard",
            questionCount = 10,
            passingPercentage = 80,
            xpReward = 1100,
            era = "Archaeological Marvels",
            iconEmoji = "🏛️"
        ),
        LevelInfo(
            levelNumber = 20,
            title = "Grand Imperial Master of History",
            subtitle = "Mastery trial spanning all world & Pakistan epochs",
            category = "All",
            difficulty = "Hard",
            questionCount = 10,
            passingPercentage = 80,
            xpReward = 1500,
            era = "The Complete Chronicle",
            iconEmoji = "👑"
        )
    )

    fun getLevel(levelNumber: Int): LevelInfo {
        return levels.firstOrNull { it.levelNumber == levelNumber } ?: levels.first()
    }
}
