import {
	Checkbox,
	List,
	ListItem,
	ListItemIcon,
	ListItemSecondaryAction,
	ListItemText
} from "@material-ui/core";
import { LocationOn } from "@material-ui/icons";

export default function RoomList(props: {
	fixRoom: boolean,
	setFixRoom: any;
}) {
	return (
        <List>
            <ListItem>
                <ListItemIcon>
                    <LocationOn />
                </ListItemIcon>
                <ListItemText>Set the room as the event's location</ListItemText>
                <ListItemSecondaryAction>
                    <Checkbox
                        edge="end"
                        checked={props.fixRoom}
                        color="primary"
                        onChange={(val: any) => props.setFixRoom(val.target.checked)}
                    />
                </ListItemSecondaryAction>
            </ListItem>
        </List>
	);
}
