import {
	Checkbox,
	List,
	ListItem,
	ListItemIcon,
	ListItemSecondaryAction,
	ListItemText,
	Tooltip
} from "@material-ui/core";
import { LocationOn } from "@material-ui/icons";
import useStyles from "../styles/ConfiguratorStyles";

export default function RoomList(props: {
	fixRoom: boolean,
	setFixRoom: any;
}) {
	const classes = useStyles();
	return (
		<List>
			<ListItem>
				<ListItemIcon>
					<LocationOn />
				</ListItemIcon>
				<ListItemText className={classes.listItemText}>
					Room as location
				</ListItemText>
				<ListItemSecondaryAction>
					<Tooltip title="Set the room as the event's location" arrow>
						<Checkbox
							edge="end"
							checked={props.fixRoom}
							color="primary"
							onChange={(val: any) => props.setFixRoom(val.target.checked)}
						/>
					</Tooltip>
				</ListItemSecondaryAction>
			</ListItem>
		</List>
	);
}
