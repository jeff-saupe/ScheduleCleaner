import validate from "./Validate";

export default function BuildURL(
	centuria: string,
	semester: string,
	exclude: { text: string }[],
	replace: { before: string; after: string }[],
	fixRoom: boolean,
	keepCodeInTitle: boolean
) {
	if (!validate.centuria(centuria) || !validate.semester(semester)) return null;

	for (const replaceItem of replace) {
		if (
			!validate.replaceText(replaceItem.before) ||
			!validate.replaceText(replaceItem.after)
		)
			return null;
	}
	const icsURL = new URL(
		`https://schedule-cleaner.herokuapp.com/cleaned-schedule/${centuria}_${semester}.ics`
	);

	const filteredExclude = exclude.filter((item) => item.text.trim().length > 0);

	
	if (filteredExclude.length > 0) {
		for (const excludeItem of exclude) {
			//if (!validate.excludeText(excludeItem.text)) return null;
	
			icsURL.searchParams.append(
				"exclude",
				`${excludeItem.text}`
			);
		}
	}

	const filteredReplace = replace.filter(
		(item) => item.before.trim().length > 0 && item.after.trim().length > 0
	);

	if (filteredReplace.length > 0) {
		for (const replaceItem of filteredReplace) {
			icsURL.searchParams.append(
				"replace",
				`${replaceItem.before};${replaceItem.after}`
			);
		}
	}

	if (keepCodeInTitle) {
		icsURL.searchParams.append("title", "keepCode");
	}

	if (fixRoom) {
		icsURL.searchParams.append("location", "");
		// Revove last = from location= because backend interprets this as a value
		return icsURL.toString().slice(0, -1);
	}

	return icsURL.toString();
}
