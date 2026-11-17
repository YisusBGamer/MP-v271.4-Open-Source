-- the weekly dragon quest's record holds its kill counts; clear the start date an old script wrote over them
UPDATE quests SET qrvalue = NULL WHERE qrkey = 503281 AND qrvalue LIKE 'startDate=%';
