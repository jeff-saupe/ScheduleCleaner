const CENTURIA_REGEX = /([a-zA-Z][0-9]{2}[a-zA-Z]?)/;
const SEMESTER_REGEX = /(.)/;

const validate = {
	centuria(centuria: string) {
		return CENTURIA_REGEX.test(centuria);
	},
	semester(semester: string) {
		return SEMESTER_REGEX.test(semester);
	},
	//excludeText(text: string) {
	//	return !text.includes(";");
	//},
	replaceText(text: string) {
		return !text.includes(";");
	},
};

export default validate;
