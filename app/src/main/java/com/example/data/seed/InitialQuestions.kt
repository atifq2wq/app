package com.example.data.seed

import com.example.model.HistoryCategories
import com.example.model.Question

object InitialQuestions {
    val list: List<Question> = listOf(
        // Pakistan Freedom Movement
        Question(
            id = "q_pak_01",
            questionText = "Which prominent leader presented the historic Lahore Resolution on 23rd March 1940 at Minto Park?",
            category = HistoryCategories.PAKISTAN_MOVEMENT,
            difficulty = "Medium",
            options = listOf(
                "Liaquat Ali Khan",
                "A.K. Fazlul Huq (Sher-e-Bangla)",
                "Chaudhry Khaliquzzaman",
                "Khawaja Nazimuddin"
            ),
            correctAnswer = "A.K. Fazlul Huq (Sher-e-Bangla)",
            explanation = "Abul Kashem Fazlul Huq, popularly honored as 'Sher-e-Bangla' (Tiger of Bengal), formally moved the historic Lahore Resolution on March 23, 1940. It was seconded by Chaudhry Khaliquzzaman, Maulana Zafar Ali Khan, and other prominent All-India Muslim League dignitaries.",
            sourceReference = "Historical Archive Record: Minto Park Assembly",
            imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuBXkqckDEJvLa4Tc4h3OF9zCKVkl_CDubhCLBFfitLchDbJtCEisb8bOyHdwCNkT0kR1Laxvo36euWqiqHmICqQKrMDCdeH60j0uWdyoy-Jivj_IZ-herCgU2CcXLTZRrbg-_G7vFN8KHIn5MFe61LK8kW5IwtIzDB8gU09tPcRP_fqPft6vIoplIRap3NzYMFeMl2iAVXhwKeGAvMRbT7FMpxc_zzo9pjn_gaUCvKJR59jzz4uzGd-",
            dateTag = "March 23, 1940",
            locationTag = "Lahore (Minar-e-Pakistan)",
            active = true
        ),
        Question(
            id = "q_pak_02",
            questionText = "In which historic city was the All-India Muslim League founded in December 1906?",
            category = HistoryCategories.PAKISTAN_MOVEMENT,
            difficulty = "Easy",
            options = listOf(
                "Dhaka",
                "Aligarh",
                "Lahore",
                "Karachi"
            ),
            correctAnswer = "Dhaka",
            explanation = "The All-India Muslim League was established during the annual meeting of the All India Muhammadan Educational Conference held at Ahsan Manzil in Dhaka in December 1906, hosted by Nawab Salimullah.",
            sourceReference = "National Archives of Pakistan: Freedom Movement Records",
            dateTag = "December 30, 1906",
            locationTag = "Dhaka, Bengal",
            active = true
        ),
        Question(
            id = "q_pak_03",
            questionText = "In his famous 1930 presidential address at Allahabad, which poet-philosopher articulated the vision for a separate Muslim homeland?",
            category = HistoryCategories.PAKISTAN_MOVEMENT,
            difficulty = "Easy",
            options = listOf(
                "Sir Syed Ahmad Khan",
                "Allama Muhammad Iqbal",
                "Maulana Muhammad Ali Jouhar",
                "Choudhry Rahmat Ali"
            ),
            correctAnswer = "Allama Muhammad Iqbal",
            explanation = "Dr. Allama Muhammad Iqbal delivered his historic Presidential Address to the 25th Session of the All-India Muslim League in Allahabad on December 29, 1930, setting forth the intellectual blueprint for a consolidated Northwestern Muslim state.",
            sourceReference = "Speeches and Statements of Iqbal",
            dateTag = "December 29, 1930",
            locationTag = "Allahabad",
            active = true
        ),
        Question(
            id = "q_pak_04",
            questionText = "Who coined the name 'Pakistan' in the pamphlet 'Now or Never; Are We to Live or Perish Forever?' published in 1933?",
            category = HistoryCategories.PAKISTAN_MOVEMENT,
            difficulty = "Medium",
            options = listOf(
                "Choudhry Rahmat Ali",
                "Liaquat Ali Khan",
                "Fazlul Huq",
                "Sir Sikandar Hayat Khan"
            ),
            correctAnswer = "Choudhry Rahmat Ali",
            explanation = "Choudhry Rahmat Ali, a Cambridge student, published the four-page leaflet 'Now or Never' on January 28, 1933, coining 'PAKSTAN' from Punjab, Afghan (NWFP), Kashmir, Sind, and BaluchisTAN.",
            sourceReference = "Cambridge Historical Pamphlet Archives",
            dateTag = "January 28, 1933",
            locationTag = "Cambridge, UK",
            active = true
        ),

        // Ancient Civilizations
        Question(
            id = "q_anc_01",
            questionText = "The ancient Indus Valley metropolis of Mohenjo-daro was primarily built along the banks of which river?",
            category = HistoryCategories.ANCIENT_CIVILIZATIONS,
            difficulty = "Medium",
            options = listOf(
                "Indus River",
                "Ravi River",
                "Ganges River",
                "Jhelum River"
            ),
            correctAnswer = "Indus River",
            explanation = "Mohenjo-daro ('Mound of the Dead') flourished along the Indus River in Larkana, Sindh, featuring sophisticated grid street planning, advanced multi-story drainage systems, and the renowned Great Bath.",
            sourceReference = "Archeological Survey of Sindh & UNESCO Archives",
            imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuBHZ5IxqfSmRz5LZZYPLL4e58TZIO076pvLgSa4pQIjyUGCdxaSh5icCQ7-WgVjy6vD2PTTi4FNF0Fn7iC31XjzOaZotxSMlUkn580MBuymvftrTh-YAo0fUgoEzfbd2qdjtJmLdI53OhvzKF7H5Fdy38zBMcPqsr492e1bu-CJoADH_I1DmBkkvHmc5f5VSb5a1N83jCj5UEg2ofVFzZNK9QGuDWyYTyTVj752OUz2ibj-1l9wwXk9",
            dateTag = "c. 2500 BCE",
            locationTag = "Sindh, Pakistan",
            active = true
        ),
        Question(
            id = "q_anc_02",
            questionText = "Which famous archaeological artifact was unearthed at Mohenjo-daro depicting an artisan girl in bronze casting?",
            category = HistoryCategories.ANCIENT_CIVILIZATIONS,
            difficulty = "Hard",
            options = listOf(
                "The Dancing Girl",
                "The Priest King",
                "The Pasupati Seal",
                "The Mother Goddess"
            ),
            correctAnswer = "The Dancing Girl",
            explanation = "The Dancing Girl is a prehistoric bronze statuette excavated by British archaeologist Ernest Mackay at Mohenjo-daro in 1926. It exemplifies advanced lost-wax casting technique from the Mature Harappan phase.",
            sourceReference = "National Museum & Archaeological Survey Collections",
            dateTag = "c. 2300–1750 BCE",
            locationTag = "Mohenjo-daro",
            active = true
        ),
        Question(
            id = "q_anc_03",
            questionText = "The ancient Code of Hammurabi, one of the oldest deciphered legal codes in human history, originated in which civilization?",
            category = HistoryCategories.ANCIENT_CIVILIZATIONS,
            difficulty = "Easy",
            options = listOf(
                "Babylonia (Mesopotamia)",
                "Ancient Egypt",
                "Persian Empire",
                "Minoan Crete"
            ),
            correctAnswer = "Babylonia (Mesopotamia)",
            explanation = "The Code of Hammurabi was enacted by the sixth Babylonian king Hammurabi circa 1754 BCE. Inscribed on a basalt stele, it established scaled justice ('an eye for an eye').",
            sourceReference = "Louvre Museum Antiquities",
            dateTag = "c. 1754 BCE",
            locationTag = "Babylon, Mesopotamia",
            active = true
        ),

        // Wars & Historic Battles
        Question(
            id = "q_war_01",
            questionText = "In which year was the First Battle of Panipat fought, where Zahir-ud-din Muhammad Babur defeated Sultan Ibrahim Lodi?",
            category = HistoryCategories.WARS_AND_BATTLES,
            difficulty = "Medium",
            options = listOf(
                "1526 CE",
                "1556 CE",
                "1761 CE",
                "1530 CE"
            ),
            correctAnswer = "1526 CE",
            explanation = "The First Battle of Panipat was fought on 21 April 1526 between the invading forces of Babur and the Lodi Empire. Babur's innovative tactical use of field artillery and matchlocks (Tulughma strategy) secured victory and founded the Mughal Empire.",
            sourceReference = "Baburnama (Memoirs of Babur)",
            dateTag = "April 21, 1526",
            locationTag = "Panipat, Haryana",
            active = true
        ),
        Question(
            id = "q_war_02",
            questionText = "The Battle of Plassey, which marked the pivotal beginning of British East India Company hegemony in Bengal, occurred in which year?",
            category = HistoryCategories.WARS_AND_BATTLES,
            difficulty = "Hard",
            options = listOf(
                "1757 CE",
                "1764 CE",
                "1857 CE",
                "1748 CE"
            ),
            correctAnswer = "1757 CE",
            explanation = "On June 23, 1757, Robert Clive defeated Nawab Siraj-ud-Daulah of Bengal at Plassey (Palashi), largely due to the defection of commander Mir Jafar.",
            sourceReference = "Imperial Gazette of India: Military Chronicles",
            dateTag = "June 23, 1757",
            locationTag = "Palashi, Bengal",
            active = true
        ),
        Question(
            id = "q_war_03",
            questionText = "The historic defense of the BRB Canal during the 1965 Indo-Pak war took place in which key sector?",
            category = HistoryCategories.WARS_AND_BATTLES,
            difficulty = "Medium",
            options = listOf(
                "Lahore (Barki / Batapur Sector)",
                "Sialkot (Chawinda)",
                "Chhamb Sector",
                "Khemkaran Sector"
            ),
            correctAnswer = "Lahore (Barki / Batapur Sector)",
            explanation = "Major Raja Aziz Bhatti Shaheed (Nishan-e-Haider) gallantly held back repeated enemy armor assaults at the Bambawali-Ravi-Bedian (BRB) Canal near Burki for several days without taking relief.",
            sourceReference = "Pakistan Army Historical Archives & Citations",
            dateTag = "September 1965",
            locationTag = "BRB Canal, Lahore",
            active = true
        ),

        // Famous Leaders
        Question(
            id = "q_lead_01",
            questionText = "Who was appointed as the first Prime Minister of Pakistan following independence in August 1947?",
            category = HistoryCategories.LEADERS,
            difficulty = "Easy",
            options = listOf(
                "Nawabzada Liaquat Ali Khan",
                "Khawaja Nazimuddin",
                "Huseyn Shaheed Suhrawardy",
                "Muhammad Ali Bogra"
            ),
            correctAnswer = "Nawabzada Liaquat Ali Khan",
            explanation = "Nawabzada Liaquat Ali Khan, revered as 'Quaid-e-Millat' (Leader of the Nation), served as the first Prime Minister of Pakistan from August 1947 until his tragic assassination in Rawalpindi in October 1951.",
            sourceReference = "Cabinet Secretariat of Pakistan Records",
            dateTag = "August 15, 1947",
            locationTag = "Karachi, Pakistan",
            active = true
        ),
        Question(
            id = "q_lead_02",
            questionText = "Which famous South African leader spent 27 years in prison before becoming the nation's first democratically elected Black president in 1994?",
            category = HistoryCategories.LEADERS,
            difficulty = "Easy",
            options = listOf(
                "Nelson Mandela",
                "Desmond Tutu",
                "Steve Biko",
                "Oliver Tambo"
            ),
            correctAnswer = "Nelson Mandela",
            explanation = "Nelson Mandela was imprisoned from 1962 to 1990 (largely on Robben Island) for resisting apartheid, eventually winning the Nobel Peace Prize and becoming President in 1994.",
            sourceReference = "Long Walk to Freedom: Autobiographical Archives",
            dateTag = "1994 CE",
            locationTag = "Pretoria, South Africa",
            active = true
        ),

        // Mughal Dynasties
        Question(
            id = "q_mughal_01",
            questionText = "The iconic red sandstone Badshahi Mosque in Lahore was commissioned by which Mughal Emperor in 1671?",
            category = HistoryCategories.MUGHAL_DYNASTIES,
            difficulty = "Medium",
            options = listOf(
                "Emperor Aurangzeb Alamgir",
                "Emperor Shah Jahan",
                "Emperor Jahangir",
                "Emperor Akbar"
            ),
            correctAnswer = "Emperor Aurangzeb Alamgir",
            explanation = "Mughal Emperor Aurangzeb Alamgir commissioned the magnificent Badshahi Mosque in 1671 opposite the Lahore Fort. It stood as the largest mosque in the world for over three centuries.",
            sourceReference = "Lahore Fort & Monument Archives",
            imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuB1gIfK_c3PldWtJRTrvPfa5L4TkqIq3LMFufoR_TOweXJ-XIEdGqwnanGBEtiXBD4agYv12N_BaVRLyuQic_B_p7LjflxVCnL5LJ1sg-oHpJCTXZ7gnOH_1U8DUQZLHhYjzP2YELl7qA6xOeifBY4x7tbeBkrwrM7lycrir4ENNSNEPKj8nitD8sa3CZMV7WW7WJh5Bg-rZG1TViffRAcIPJU23zOcIbab3oClRw1sNe8sbylUNoVP",
            dateTag = "1671–1673 CE",
            locationTag = "Lahore, Pakistan",
            active = true
        ),
        Question(
            id = "q_mughal_02",
            questionText = "Which Mughal Emperor founded the Shalimar Gardens in Lahore in 1641 CE?",
            category = HistoryCategories.MUGHAL_DYNASTIES,
            difficulty = "Medium",
            options = listOf(
                "Shah Jahan",
                "Akbar",
                "Babur",
                "Humayun"
            ),
            correctAnswer = "Shah Jahan",
            explanation = "The Shalimar Gardens were laid out under the supervision of Khalilullah Khan during the reign of Emperor Shah Jahan, renowned as the 'Architect King' of the Mughal Empire.",
            sourceReference = "UNESCO World Heritage Inscription Data",
            dateTag = "1641 CE",
            locationTag = "Lahore, Pakistan",
            active = true
        ),

        // Science & Inventions
        Question(
            id = "q_sci_01",
            questionText = "Which 9th-century polymath from the House of Wisdom in Baghdad is revered as the father of Algebra?",
            category = HistoryCategories.SCIENCE_INVENTIONS,
            difficulty = "Easy",
            options = listOf(
                "Muhammad ibn Musa al-Khwarizmi",
                "Ibn al-Haytham",
                "Al-Biruni",
                "Jabir ibn Hayyan"
            ),
            correctAnswer = "Muhammad ibn Musa al-Khwarizmi",
            explanation = "Al-Khwarizmi wrote 'Kitab al-Jabr wa-l-Muqabala', from which the term 'algebra' is derived. His works introduced Hindu-Arabic numerals and algorithms to Europe.",
            sourceReference = "House of Wisdom Scholarly Records",
            imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuBdyJDBZuFDnYIjOhZ78uKeO_ZujP2LCl1Z8dmSNS_zwpbtXnb4jOuU9TlnU-zsdHRrIWBrGRJ5X_9qNOEtOjmTVPWFaW8Q1GWbCXqexsEQFAQmEXrS0JdeS_dw8gFIgsygkTWk3GYVLa4BAEA2ZvTwXZtuTY7QwNO-0TcLkmN9UrG8XWB8qwYJ5JFlM7foN3EcZjM-CoVQHXMlgGRsH9aDOnZb52KzY_7n-broOudk8OMlXFt4B9D3",
            dateTag = "c. 820 CE",
            locationTag = "Baghdad, Abbasid Caliphate",
            active = true
        ),
        Question(
            id = "q_sci_02",
            questionText = "Johannes Gutenberg introduced movable mechanical type printing to Europe around which decade?",
            category = HistoryCategories.SCIENCE_INVENTIONS,
            difficulty = "Medium",
            options = listOf(
                "1440s–1450s",
                "1380s",
                "1520s",
                "1600s"
            ),
            correctAnswer = "1440s–1450s",
            explanation = "Gutenberg completed his movable type printing press in Mainz around 1440 and published the 42-Line Gutenberg Bible circa 1455, revolutionizing human knowledge transmission.",
            sourceReference = "Mainz Gutenberg Museum Archives",
            dateTag = "c. 1450 CE",
            locationTag = "Mainz, Holy Roman Empire",
            active = true
        ),

        // UNESCO World Heritage
        Question(
            id = "q_unesco_01",
            questionText = "Which UNESCO World Heritage site in Punjab, Pakistan, was built by Sher Shah Suri in 1541 to subdue the rebellious Ghakhar tribes?",
            category = HistoryCategories.UNESCO_HERITAGE,
            difficulty = "Medium",
            options = listOf(
                "Rohtas Fort",
                "Derawar Fort",
                "Attock Fort",
                "Kot Diji Fort"
            ),
            correctAnswer = "Rohtas Fort",
            explanation = "Rohtas Fort (Qila Rohtas) near Jhelum was constructed under the orders of Sher Shah Suri between 1541 and 1548. It is an exceptional example of early Muslim military architecture.",
            sourceReference = "UNESCO World Heritage Site No. 585",
            imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuA3MFHsfE7V6baTBe0TwnwOoieMmmtIfI5HvJ_XPxIpKikOFUHKRvjJHrOw6I2T79zpr88tUQvT0fbWlS2cty4bMuxHJ75yJIWnmoYYJ7eisOMuzhV7Zw05WCGZyL7KWntApVnfvxLtbeQeNzBWIC9w10p_PcC8cYVe4Yr_hJqQ0BROz_zD8NbSk_8P6zThOgKsu2QyG7sdT31jHuiGTESKMClZHgSA_DFMasj3gZrWKs0qYAzlGZEZ",
            dateTag = "1541–1548 CE",
            locationTag = "Jhelum, Pakistan",
            active = true
        ),
        Question(
            id = "q_unesco_02",
            questionText = "Taxila, an ancient seat of Gandhara Buddhist learning and Greco-Buddhist art, is located near which modern city in Pakistan?",
            category = HistoryCategories.UNESCO_HERITAGE,
            difficulty = "Easy",
            options = listOf(
                "Rawalpindi / Islamabad",
                "Peshawar",
                "Multan",
                "Quetta"
            ),
            correctAnswer = "Rawalpindi / Islamabad",
            explanation = "Taxila lies approximately 32 km northwest of Islamabad and Rawalpindi. It was declared a UNESCO World Heritage site in 1980 for its ruins of Dharmarajika, Sirkap, and Jaulian.",
            sourceReference = "Gandhara Archaeological Survey",
            dateTag = "c. 6th century BCE",
            locationTag = "Taxila, Punjab",
            active = true
        ),

        // Modern History
        Question(
            id = "q_mod_01",
            questionText = "The United Nations (UN) was officially established on 24 October 1945 following the ratification of its Charter in which city?",
            category = HistoryCategories.MODERN_HISTORY,
            difficulty = "Easy",
            options = listOf(
                "San Francisco",
                "New York",
                "Geneva",
                "London"
            ),
            correctAnswer = "San Francisco",
            explanation = "Delegates from 50 nations met at the San Francisco Conference between April and June 1945 to sign the United Nations Charter, which officially entered into force on October 24, 1945.",
            sourceReference = "UN Official Archives Record",
            dateTag = "October 24, 1945",
            locationTag = "San Francisco, USA",
            active = true
        ),
        Question(
            id = "q_mod_02",
            questionText = "The current Constitution of the Islamic Republic of Pakistan was unanimously passed by the National Assembly in which year?",
            category = HistoryCategories.MODERN_HISTORY,
            difficulty = "Medium",
            options = listOf(
                "1973",
                "1956",
                "1962",
                "1971"
            ),
            correctAnswer = "1973",
            explanation = "The 1973 Constitution of Pakistan was passed on April 10, 1973, and ratified on August 14, 1973. It established a parliamentary democracy with a bicameral legislature.",
            sourceReference = "National Assembly of Pakistan Archives",
            dateTag = "August 14, 1973",
            locationTag = "Islamabad, Pakistan",
            active = true
        )
    )
}
