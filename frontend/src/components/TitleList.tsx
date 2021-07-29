import {
	Checkbox,
	List,
	ListItem,
	ListItemIcon,
	ListItemSecondaryAction,
	ListItemText,
	Tooltip
} from "@material-ui/core";
import { Title } from "@material-ui/icons";
import useStyles from "../styles/ConfiguratorStyles";

export default function TitleList(props: {
	keepCodeInTitle: boolean,
	setKeepCodeInTitle: any;
}) {
	const classes = useStyles();
	return (
		<List>
			<ListItem>
				<ListItemIcon>
					<Title />
				</ListItemIcon>
				<ListItemText className={classes.listItemText}>
					Keep module code
				</ListItemText>
				<ListItemSecondaryAction>
					<Tooltip title="Keep the module code (e.g. W118) in the title" arrow>
						<Checkbox
							edge="end"
							checked={props.keepCodeInTitle}
							color="primary"
							onChange={(val: any) => props.setKeepCodeInTitle(val.target.checked)}
						/>
					</Tooltip>
				</ListItemSecondaryAction>
			</ListItem>
		</List>
	);
}
